package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.models.Product;
import io.vertx.core.Future;
import java.util.List;

public interface ProductRepository {
  Future<Void> createProductTable();
  Future<List<Product>> findProducts(List<String> queryWhereClauseCriteria);
  Future<Product> saveProduct(Product newProduct);
  Future<Boolean> removeProduct(Long productId);
}
