package com.product.affiliation.backend.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ProductPayload {
  @JsonProperty
  private final Long id;

  public ProductPayload(Long id) {
    this.id = id;
  }

  public abstract String getType();

  public final Long getId() {
    return id;
  }
}
