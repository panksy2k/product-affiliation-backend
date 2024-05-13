package com.product.affiliation.backend.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteProductEventPayload extends ProductEventPayload {
  static final String TYPE = "DELETE_PRODUCT";

  public DeleteProductEventPayload(@JsonProperty("id") Long id) {
    super(id);
  }

  @Override
  public String getType() {
    return TYPE;
  }

}
