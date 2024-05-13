package com.product.affiliation.backend.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="type")
@JsonSubTypes({
  @JsonSubTypes.Type(value= GetProductsEventPayload.class, name= GetProductsEventPayload.TYPE),
  @JsonSubTypes.Type(value= UpdateProductEventPayload.class, name= UpdateProductEventPayload.TYPE),
  @JsonSubTypes.Type(value= DeleteProductEventPayload.class, name= DeleteProductEventPayload.TYPE)
})
public abstract class ProductEventPayload {
  @JsonProperty
  private final Long id;

  public ProductEventPayload(Long id) {
    this.id = id;
  }

  public abstract String getType();

  public final Long getId() {
    return id;
  }
}
