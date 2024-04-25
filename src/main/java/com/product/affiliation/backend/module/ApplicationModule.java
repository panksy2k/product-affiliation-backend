package com.product.affiliation.backend.module;

import com.google.inject.AbstractModule;
import com.product.affiliation.backend.errors.DependencyCreationException;
import com.product.affiliation.backend.messaging.ProductMessageKafkaConsumerVerticle;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import com.product.affiliation.backend.messaging.receiver.ProductPipelinedReceiver;
import com.product.affiliation.backend.models.ApplicationConfiguration;
import com.product.affiliation.backend.repositories.ProductRepository;
import com.product.affiliation.backend.repositories.ProductRepositoryImpl;
import com.product.affiliation.backend.services.ProductService;
import com.product.affiliation.backend.services.ProductServiceImpl;
import com.product.affiliation.backend.verticles.ProductVerticle;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.SqlClient;
import java.time.Duration;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationModule.class);
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final EventReceiver productFetchEventPipelineReceiver;

    public ApplicationModule(Vertx vertx, ApplicationConfiguration envAppConfig) throws DependencyCreationException {
        SqlClient dbClient = PgPool.client(vertx, envAppConfig.getDatabaseUrl());
        productRepository = new ProductRepositoryImpl(vertx, dbClient);
        productRepository.createProductTable()
                .onSuccess(h -> LOG.info("==== Create Product Table - successful! ===="))
                .onFailure(h -> {
                  h.printStackTrace();
                  LOG.error("=== Error whilst creating Product Table -- {}", h);
                });

        productFetchEventPipelineReceiver = new ProductPipelinedReceiver(vertx, kafkaConsumerProperties(),
          "product", Duration.ofMillis(100), 10);

        productService = new ProductServiceImpl(productRepository, productFetchEventPipelineReceiver);
    }

    private Map<String, String> kafkaConsumerProperties() {
        Map<String, String> consumerConfig =
                Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "product-group",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return consumerConfig;
    }

    @Override
    protected void configure() {
        bind(ProductVerticle.class).toInstance(new ProductVerticle());
        bind(ProductMessageKafkaConsumerVerticle.class).toInstance(new ProductMessageKafkaConsumerVerticle(productService));
    }
}
