package com.product.affiliation.backend.messaging.receiver;

import com.product.affiliation.backend.messaging.event.ProductEventPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ReceiveEvent {
  private final ProductEventPayload payload;

  private final Throwable error;

  private final ConsumerRecord<String, ?> record;

  private final String encodedValue;

  public ReceiveEvent(ProductEventPayload payload, Throwable error, ConsumerRecord<String, ?> record, String encodedValue) {
    this.record = record;
    this.payload = payload;
    this.error = error;
    this.encodedValue = encodedValue;
  }

  public ProductEventPayload getPayload() {
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
