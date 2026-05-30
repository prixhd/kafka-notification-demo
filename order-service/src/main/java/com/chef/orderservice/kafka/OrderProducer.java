package com.chef.orderservice.kafka;

import com.chef.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${app.kafka.topic.order-events}")
    private String topic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {
        log.info("Sending order event to topic [{}]: orderId={}", topic, event.getOrderId());

        CompletableFuture<SendResult<String, OrderEvent>> future =
                kafkaTemplate.send(topic, event.getOrderId(), event);

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error(
                        "Failed to send order event: orderId={}, error={}",
                        event.getOrderId(),
                        throwable.getMessage()
                );
            } else {
                log.info(
                        "Order event sent successfully: orderId={}, partition={}, offset={}",
                        event.getOrderId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            }
        });
    }
}