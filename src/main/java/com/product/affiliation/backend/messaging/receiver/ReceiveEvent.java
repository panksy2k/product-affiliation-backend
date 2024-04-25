package com.product.affiliation.backend.messaging.receiver;

import com.product.affiliation.backend.messaging.event.ProductPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ReceiveEvent {
  private final ProductPayload payload;

  private final Throwable error;

  private final ConsumerRecord<String, ?> record;

  private final String encodedValue;

  public ReceiveEvent(ProductPayload payload, Throwable error, ConsumerRecord<String, ?> record, String encodedValue) {
    this.record = record;
    this.payload = payload;
    this.error = error;
    this.encodedValue = encodedValue;
  }

  public ProductPayload getPayload() {
    return payload;
  }

  public boolean isError() {
    return error != null;
  }

  public Throwable getError() {
    return error;
  }

  public ConsumerRecord<String, ?> getRecord() {
    return record;
  }

  public String getEncodedValue() {
    return encodedValue;
  }

  @Override
  public String toString() {
    return ReceiveEvent.class.getSimpleName() + " [payload=" + payload + ", error=" + error +
      ", record=" + record + ", encodedValue=" + encodedValue + "]";
  }
}
