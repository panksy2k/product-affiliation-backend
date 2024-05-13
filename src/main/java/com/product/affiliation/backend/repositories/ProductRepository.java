package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import io.vertx.core.Future;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  Future<Void> createProductTable();
  Future<List<ProductResponseEventPayload>> findProducts(GetProductsEventPayload productFetchCriteria);
  Future<ProductResponseEventPayload> saveProduct(ProductResponseEventPayload newProduct);
  Future<Boolean> removeProduct(Long productId);
  Future<Optional<ProductResponseEventPayload>> findProduct(Long productId);
}
