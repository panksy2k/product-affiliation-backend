package com.product.affiliation.backend.verticles;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.product.affiliation.backend.config.ApplicationConfig;
import com.product.affiliation.backend.config.EmbeddedApplicationConfig;
import com.product.affiliation.backend.errors.DependencyCreationException;
import com.product.affiliation.backend.module.ApplicationModule;
import com.product.affiliation.backend.services.ProductService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(ProductVerticle.class);

    private final ProductService productService;
    public ProductVerticle(ProductService productService) {
        this.productService = productService;
    }

    public static void main(String[] args) throws Exception {
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
                    return injector.getInstance(ProductVerticle.class);
                })
                .compose(vertx::deployVerticle)
                .onFailure(errorVerticle -> {
                    LOG.error(errorVerticle.getMessage());
                    vertx.close();
                })
                .onSuccess(resultId -> {
                  System.out.println("Product verticle started successfully -- " + resultId);
                });
    }

  @Override
  public void start() throws Exception {
      this.productService.getProductEventReceiver().start();
  }

    @Override
    public void stop() throws Exception {
        this.productService.getProductEventReceiver().close();
    }
}
