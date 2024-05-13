package com.product.affiliation.backend.services;

import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import io.vertx.core.Future;
import java.util.List;
import java.util.Optional;

public interface ProductService {
  Future<ProductResponseEventPayload> createProduct(ProductResponseEventPayload newProduct);
  Future<List<ProductResponseEventPayload>> findProducts(GetProductsEventPayload filterCriteria);
  Future<Boolean> removeProduct(long productId);
  Future<ProductResponseEventPayload> updateProduct(ProductResponseEventPayload productExisting);
  Future<Optional<ProductResponseEventPayload>> findProduct(long productId);
  EventReceiver getProductEventReceiver();
}
