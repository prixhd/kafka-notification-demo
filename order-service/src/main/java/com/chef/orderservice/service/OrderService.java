package com.chef.orderservice.service;

import com.chef.orderservice.dto.OrderRequest;
import com.chef.orderservice.kafka.OrderProducer;
import com.chef.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderProducer orderProducer;

    public OrderEvent createOrder(OrderRequest request) {
        OrderEvent event = OrderEvent.builder()
                .orderId(UUID.randomUUID().toString())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .totalPrice(
                        request.getPricePerUnit()
                                .multiply(java.math.BigDecimal.valueOf(request.getQuantity()))
                )
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Order created: orderId={}, customer={}",
                event.getOrderId(), event.getCustomerName());

        orderProducer.sendOrderEvent(event);

        return event;
    }
}