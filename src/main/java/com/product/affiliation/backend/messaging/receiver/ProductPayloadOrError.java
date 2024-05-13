package com.product.affiliation.backend.messaging.receiver;

import com.product.affiliation.backend.messaging.event.ProductEventPayload;

public final class ProductPayloadOrError {
  private final ProductEventPayload payload;

  private final Throwable error;

  private String encodedValue;

  public ProductPayloadOrError(ProductEventPayload payload, Throwable error, String encodedValue) {
    this.payload = payload;
    this.error = error;
    this.encodedValue = encodedValue;
  }

  public ProductEventPayload getPayload() {
    return payload;
  }

  public Throwable getError() {
    return error;
  }

  public String getEncodedValue() {
    return encodedValue;
  }

  @Override
  public String toString() {
    return ProductPayloadOrError.class.getSimpleName() + " [payload=" + payload +
        ", error=" + error + ", encodedValue=" + encodedValue + "]";
  }
}
