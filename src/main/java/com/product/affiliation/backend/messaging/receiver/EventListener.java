package com.product.affiliation.backend.messaging.receiver;

@FunctionalInterface
public interface EventListener {
  void onEvent(ReceiveEvent event);
}
