package com.product.affiliation.backend.messaging.receiver;

import java.io.Closeable;

public interface EventReceiver extends Closeable {
  void start();

  void addListener(EventListener listener);
}
