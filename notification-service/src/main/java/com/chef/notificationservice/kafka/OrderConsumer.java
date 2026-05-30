package com.chef.notificationservice.kafka;

import com.chef.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${app.kafka.topic.order-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(Map<String, Object> orderEvent) {
        log.info("Received order event from Kafka: {}", orderEvent);

        try {
            String orderId      = (String) orderEvent.get("orderId");
            String customerEmail = (String) orderEvent.get("customerEmail");
            String customerName  = (String) orderEvent.get("customerName");
            String productName   = (String) orderEvent.get("productName");
            String status        = (String) orderEvent.get("status");

            // базовая проверка перед обработкой
            if (orderId == null || customerEmail == null) {
                log.warn("Received incomplete order event, skipping: {}", orderEvent);
                return;
            }

            notificationService.processOrderNotification(
                    orderId,
                    customerEmail,
                    customerName,
                    productName,
                    status
            );

        } catch (Exception e) {
            log.error("Error processing order event: {}, error: {}",
                    orderEvent, e.getMessage(), e);
        }
    }
}