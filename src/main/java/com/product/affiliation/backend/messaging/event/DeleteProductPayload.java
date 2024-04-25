package com.product.affiliation.backend.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteProductPayload extends ProductPayload {
  static final String TYPE = "DELETE_PRODUCT";

  public DeleteProductPayload(@JsonProperty("id") Long id) {
    super(id);
  }

  @Override
  public String getType() {
    return TYPE;
  }

}
