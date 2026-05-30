package com.chef.notificationservice.controller;

import com.chef.notificationservice.model.Notification;
import com.chef.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "API для просмотра уведомлений")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @Operation(summary = "Получить все уведомления")
    public List<Notification> getAllNotifications() {
        return notificationService.getAll();
    }

    @GetMapping("/by-email")
    @Operation(summary = "Получить уведомления по email")
    public List<Notification> getByEmail(
            @Parameter(description = "Email покупателя", example = "ivan@example.com")
            @RequestParam String email) {
        return notificationService.getByEmail(email);
    }

    @GetMapping("/by-order/{orderId}")
    @Operation(summary = "Получить уведомления по ID заказа")
    public List<Notification> getByOrderId(
            @Parameter(description = "ID заказа")
            @PathVariable String orderId) {
        return notificationService.getByOrderId(orderId);
    }
}