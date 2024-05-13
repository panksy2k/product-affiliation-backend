package com.product.affiliation.backend.services;

import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import com.product.affiliation.backend.messaging.receiver.ReceiveEvent;
import com.product.affiliation.backend.repositories.ProductRepository;
import io.vertx.core.Future;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProductServiceImpl implements ProductService {
  private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class.getSimpleName());
  private final ProductRepository productRepository;
  private final EventReceiver productEventReceiver;

  public ProductServiceImpl(ProductRepository productRepository, EventReceiver eventReceiver) {
    this.productRepository = productRepository;

    this.productEventReceiver = eventReceiver;
    this.productEventReceiver.addListener(this::onEventCapture);
  }

  @Override
  public Future<ProductResponseEventPayload> createProduct(ProductResponseEventPayload newProduct) {
    return productRepository.saveProduct(newProduct);
  }

  @Override
  public Future<List<ProductResponseEventPayload>> findProducts(GetProductsEventPayload filterCriteria) {
    if(filterCriteria == null) {
      return Future.failedFuture("Empty payload criteria");

    }

    return this.productRepository.findProducts(filterCriteria);
  }

  @Override
  public Future<Boolean> removeProduct(long productId) {
    return productRepository.removeProduct(productId);
  }

  @Override
  public Future<ProductResponseEventPayload> updateProduct(ProductResponseEventPayload productExisting) {
    return null;
  }

  @Override
  public Future<Optional<ProductResponseEventPayload>> findProduct(long productId) {
    return productRepository.findProduct(productId);
  }

  @Override
  public EventReceiver getProductEventReceiver() {
    return productEventReceiver;
  }

  private void onEventCapture(ReceiveEvent event) {
    if(event.isError()) {
      System.err.format("Error whilst receiving data for product %s -- %s%n", event.getRecord(), event.getError());
    } else {
      System.err.format("Received data for product %s%n", event.getPayload());
    }
  }
}
