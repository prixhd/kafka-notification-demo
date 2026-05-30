package com.chef.notificationservice.repository;

import com.chef.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByOrderId(String orderId);

    List<Notification> findByCustomerEmail(String customerEmail);
}