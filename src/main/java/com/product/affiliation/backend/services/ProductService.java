package com.product.affiliation.backend.services;

import com.product.affiliation.backend.messaging.event.GetProductPayload;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Optional;

public interface ProductService {
  Future<GetProductPayload> createProduct(GetProductPayload newProduct);
  Future<List<GetProductPayload>> findProducts(JsonObject filterCriteria);
  Future<Boolean> removeProduct(long productId);
  Future<GetProductPayload> updateProduct(GetProductPayload productExisting);
  Future<Optional<GetProductPayload>> findProduct(long productId);
  EventReceiver getProductEventReceiver();
}
