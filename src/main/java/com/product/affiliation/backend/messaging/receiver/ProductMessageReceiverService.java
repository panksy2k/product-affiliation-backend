package com.product.affiliation.backend.messaging.receiver;

public class ProductMessageReceiverService {

    public ProductMessageReceiverService(EventReceiver productPipelineReceiver) {
        productPipelineReceiver.addListener(this::onEventCapture);
    }

    private void onEventCapture(ReceiveEvent event) {
      //TODO
    }
}
