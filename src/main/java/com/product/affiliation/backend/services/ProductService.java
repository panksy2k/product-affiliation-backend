package com.product.affiliation.backend.services;

import com.product.affiliation.backend.models.Product;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Optional;

public interface ProductService {
  Future<Product> createProduct(Product newProduct);
  Future<List<Product>> findProducts(JsonObject filterCriteria);
  Future<Boolean> removeProduct(long productId);
  Future<Product> updateProduct(Product productExisting);
  Future<Optional<Product>> findProduct(long productId);
}
