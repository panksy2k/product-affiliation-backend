package com.product.affiliation.backend.messaging.receiver;

import com.product.affiliation.backend.messaging.event.ProductPayload;

public final class ProductPayloadOrError {
  private final ProductPayload payload;

  private final Throwable error;

  private String encodedValue;

  public ProductPayloadOrError(ProductPayload payload, Throwable error, String encodedValue) {
    this.payload = payload;
    this.error = error;
    this.encodedValue = encodedValue;
  }

  public ProductPayload getPayload() {
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
