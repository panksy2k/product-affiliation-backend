package com.product.affiliation.backend.messaging;

import com.product.affiliation.backend.services.ProductService;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductMessageKafkaConsumerVerticle extends AbstractVerticle  {
  private static final Logger LOG = LoggerFactory.getLogger(ProductMessageKafkaConsumerVerticle.class);
  private final ProductService ps;

  public ProductMessageKafkaConsumerVerticle(ProductService productService) {
    this.ps = productService;
  }

  @Override
  public void start() {
    ps.getProductEventReceiver().start();
  }


  @Override
  public void stop() throws Exception {
    ps.getProductEventReceiver().close();
  }
}
