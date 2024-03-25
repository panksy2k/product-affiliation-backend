package com.product.affiliation.backend.verticles;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.product.affiliation.backend.config.ApplicationConfig;
import com.product.affiliation.backend.config.EmbeddedApplicationConfig;
import com.product.affiliation.backend.errors.AccessDeniedException;
import com.product.affiliation.backend.errors.DependencyCreationException;
import com.product.affiliation.backend.errors.ValidationException;
import com.product.affiliation.backend.module.ApplicationModule;
import com.product.affiliation.backend.web.ProductController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(ProductVerticle.class);

    private final ProductController _controller;

    public ProductVerticle(ProductController controller) {
        this._controller = controller;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ApplicationConfig config = new EmbeddedApplicationConfig(vertx);
        config.receiveApplicationConfig()
                .compose(appConfig -> {
                    try {
                        ApplicationModule appModule = new ApplicationModule(vertx, appConfig);
                        return Future.succeededFuture(appModule);
                    } catch (DependencyCreationException e) {
                        return Future.failedFuture(e);
                    }
                })
                .map(module -> {
                    Injector injector = Guice.createInjector(module);
                    ProductVerticle mainVerticle = injector.getInstance(ProductVerticle.class);
                    return mainVerticle;
                })
                .compose(vertx::deployVerticle)
                .onFailure(errorVerticle -> {
                    System.out.println(errorVerticle.getMessage());
                    vertx.close();
                })
                .onSuccess(resultId -> {
                    LOG.info("Product verticle Started Successfully -- " + resultId);
                });
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route(HttpMethod.POST, "/product/all").handler(_controller::getProducts);

        router.route("/product/*").failureHandler(context -> {
          if (context.failed()) {
            if (context.failed()) {
              Throwable reason = context.failure();

              if (reason instanceof ValidationException) {
                ValidationException exception = (ValidationException) reason;
                JsonArray validatorMessages = exception.getMessages();
                LOG.error(validatorMessages.encode());

                context.response().setStatusCode(400).end(validatorMessages.encode());
              } else if (reason instanceof AccessDeniedException) {
                context.response().setStatusCode(403).end();
              } else {
                context.response().setStatusCode(500).end("Error happened");
              }
            }
          }
        });

        httpServer.requestHandler(router);
        httpServer.listen(9191)
          .onFailure(startPromise::fail)
          .onSuccess(h -> {
            startPromise.complete();
            LOG.info("HTTP server started on port 9191");
          });
    }
}
