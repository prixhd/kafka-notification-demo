package com.chef.notificationservice.service;

import com.chef.notificationservice.model.Notification;
import com.chef.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void processOrderNotification(
            String orderId,
            String customerEmail,
            String customerName,
            String productName,
            String status
    ) {
        String message = String.format(
                "Здравствуйте, %s! Ваш заказ #%s на товар '%s' имеет статус: %s.",
                customerName, orderId, productName, status
        );

        Notification notification = Notification.builder()
                .orderId(orderId)
                .customerEmail(customerEmail)
                .message(message)
                .status("SENT")
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        log.info("Notification saved: orderId={}, email={}, message={}",
                orderId, customerEmail, message);
    }

    public List<Notification> getByEmail(String email) {
        return notificationRepository.findByCustomerEmail(email);
    }

    public List<Notification> getByOrderId(String orderId) {
        return notificationRepository.findByOrderId(orderId);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }
}