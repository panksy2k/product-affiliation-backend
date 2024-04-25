package com.product.affiliation.backend.services;

import com.product.affiliation.backend.messaging.event.GetProductPayload;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import com.product.affiliation.backend.messaging.receiver.ReceiveEvent;
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
  private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class.getSimpleName());
  private final ProductRepository productRepository;
  private final EventReceiver productEventReceiver;

  public ProductServiceImpl(ProductRepository productRepository, EventReceiver eventReceiver) {
    this.productRepository = productRepository;
    this.productEventReceiver = eventReceiver;

    this.productEventReceiver.addListener(this::onEventCapture);
  }

  @Override
  public Future<GetProductPayload> createProduct(GetProductPayload newProduct) {
    return productRepository.saveProduct(newProduct);
  }

  @Override
  public Future<List<GetProductPayload>> findProducts(JsonObject filterCriteria) {
    Set<String> allJsonElements = filterCriteria.fieldNames();

    List<String> queryWhereClause = new ArrayList<>();
    for (String columnName : allJsonElements) {
      queryWhereClause.add(QueryHelper.createExpression(columnName, filterCriteria.getJsonObject(columnName)));
    }

    return this.productRepository.findProducts(queryWhereClause);
  }

  @Override
  public Future<Boolean> removeProduct(long productId) {
    return productRepository.removeProduct(productId);
  }

  @Override
  public Future<GetProductPayload> updateProduct(GetProductPayload productExisting) {
    return null;
  }

  @Override
  public Future<Optional<GetProductPayload>> findProduct(long productId) {
    return productRepository.findProduct(productId);
  }

  @Override
  public EventReceiver getProductEventReceiver() {
    return productEventReceiver;
  }

  private void onEventCapture(ReceiveEvent event) {
    //TODO - call the service & pass the query (event)

  }

}
