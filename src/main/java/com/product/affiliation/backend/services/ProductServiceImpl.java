package com.product.affiliation.backend.services;

import com.product.affiliation.backend.models.Product;
import com.product.affiliation.backend.repositories.ProductRepository;
import com.product.affiliation.backend.util.QueryHelper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = Logger.getLogger(ProductService.class.getSimpleName());

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Future<Product> createProduct(Product newProduct) {
        return productRepository.saveProduct(newProduct);
    }

    @Override
    public Future<List<Product>> findProducts(JsonObject filterCriteria) {
      Set<String> allJsonElements = filterCriteria.fieldNames();

      List<String> queryWhereClause = new ArrayList<>();
      for(String columnName : allJsonElements) {
        queryWhereClause.add(QueryHelper.createExpression(columnName, filterCriteria.getJsonObject(columnName)));
      }

      return this.productRepository.findProducts(queryWhereClause);
    }

    @Override
    public Future<Boolean> removeProduct(long productId) {
        return productRepository.removeProduct(productId);
    }

    @Override
    public Future<Product> updateProduct(Product productExisting) {
        return null;
    }

    @Override
    public Future<Optional<Product>> findProduct(long productId) {
        return null;
    }
}
