package com.product.affiliation.backend.messaging.receiver;

import com.obsidiandynamics.worker.Terminator;
import com.obsidiandynamics.worker.WorkerOptions;
import com.obsidiandynamics.worker.WorkerThread;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPipelinedReceiver implements EventReceiver {
  private static final Logger LOG = LoggerFactory.getLogger(ProductPipelinedReceiver.class);

  private final Set<EventListener> listeners = new HashSet<>();
  private final BlockingQueue<ReceiveEvent> receivedEventsQueue;
  private final Duration pollTimeout;
  private final KafkaConsumer<String, ProductPayloadOrError> kconsumer;

  private final WorkerThread processingThread;

  public ProductPipelinedReceiver(Vertx vertx,
                                  Map<String, String> consumerGenericConfig,
                                  String topicName,
                                  Duration pollTime,
                                  int queueCapacity) {
    receivedEventsQueue = new LinkedBlockingQueue<>(queueCapacity);

    pollTimeout = pollTime;

    Map<String, String> consumerFinalMergedConfig = Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
        ConsumerConfig.GROUP_ID_CONFIG, "product-group",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

    consumerFinalMergedConfig.putAll(consumerGenericConfig);

    kconsumer = KafkaConsumer.create(vertx, consumerFinalMergedConfig);

    kconsumer.subscribe(topicName)
      .onSuccess(h -> {
        LOG.info("Subscribed on {}", topicName);

        vertx.setPeriodic(1000L, timerId ->
          kconsumer.poll(pollTimeout)
            .onSuccess(records -> {
                  for(ConsumerRecord<String, ProductPayloadOrError> record : records.records()) {
                    ProductPayloadOrError payloadOrError = record.value();
                    ReceiveEvent receivedEvent = new ReceiveEvent(payloadOrError.getPayload(), payloadOrError.getError(), record, payloadOrError.getEncodedValue());

                    try {
                        receivedEventsQueue.put(receivedEvent);
                    } catch(InterruptedException e) {
                        LOG.error(e.getMessage());
                    }
                  }
            })
            .onFailure(err -> {
              LOG.error(err.getMessage());
              vertx.cancelTimer(timerId);
            })
        );
      });

    kconsumer.commit().onSuccess(h -> LOG.info("Offset committed now"));

    processingThread = WorkerThread.builder()
      .withOptions(new WorkerOptions().daemon().withName(ProductPipelinedReceiver.class, "product-processing"))
      .onCycle(this::processCycle)
      .build();
  }

  private void processCycle(WorkerThread t) throws InterruptedException {
      ReceiveEvent incomingEvent = receivedEventsQueue.take();
      LOG.info("Event taken from thread {}", Thread.currentThread().getName());

      fireEvent(incomingEvent);
  }

  @Override
  public void start() {
      processingThread.start();
  }

  @Override
  public void addListener(EventListener listener) {
    listeners.add(listener);
  }

  @Override
  public void close() throws IOException {
    Terminator.of(processingThread).terminate().joinSilently();
    kconsumer.close().onSuccess(h -> LOG.info("KafkaConsumer is closed")).onFailure(e -> LOG.error("KafkaConsumer could not be closed due to " + e.getMessage()));
  }

  private void fireEvent(ReceiveEvent event) {
    for(EventListener l : listeners) {
      l.onEvent(event);
    }
  }
}
