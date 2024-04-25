package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.messaging.event.GetProductPayload;
import io.vertx.core.Future;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  Future<Void> createProductTable();
  Future<List<GetProductPayload>> findProducts(List<String> queryWhereClauseCriteria);
  Future<GetProductPayload> saveProduct(GetProductPayload newProduct);
  Future<Boolean> removeProduct(Long productId);
  Future<Optional<GetProductPayload>> findProduct(Long productId);
}
