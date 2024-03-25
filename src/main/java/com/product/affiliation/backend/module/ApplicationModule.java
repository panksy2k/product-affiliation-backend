package com.product.affiliation.backend.module;

import com.google.inject.AbstractModule;
import com.product.affiliation.backend.errors.DependencyCreationException;
import com.product.affiliation.backend.models.ApplicationConfiguration;
import com.product.affiliation.backend.repositories.ProductRepository;
import com.product.affiliation.backend.repositories.ProductRepositoryImpl;
import com.product.affiliation.backend.services.ProductService;
import com.product.affiliation.backend.services.ProductServiceImpl;
import com.product.affiliation.backend.verticles.ProductVerticle;
import com.product.affiliation.backend.web.ProductController;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.SqlClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationModule.class);

    private final ProductService productService;
    private final ProductController productController;
    private final ProductRepository productRepository;

    public ApplicationModule(Vertx vertx, ApplicationConfiguration envAppConfig) throws DependencyCreationException {
        SqlClient dbClient = PgPool.client(vertx, envAppConfig.getDatabaseUrl());

        productRepository = new ProductRepositoryImpl(vertx, dbClient);

        productService = new ProductServiceImpl(productRepository);

        productController = new ProductController(productService);

        productRepository.createProductTable()
                .onSuccess(h -> System.out.println("Create Product Table - successful!"))
                .onFailure(h -> System.out.println("Error whilst creating Product Table!"));
    }

    @Override
    protected void configure() {
        bind(ProductVerticle.class).toInstance(new ProductVerticle(productController));
    }
}
