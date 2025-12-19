package com.defence.portal.service;

import com.defence.portal.dto.NotificationDto;
import com.defence.portal.entity.Notification;

import java.util.List;

public interface NotificationService {

    // Create / Publish a new exam notification
    Notification createNotification(NotificationDto notificationDto);

    // Update existing notification
    Notification updateNotification(Long id, NotificationDto notificationDto);

    // Get single notification by ID (DTO for GET)
    NotificationDto getNotificationById(Long id);

    // Get all notifications (DTO for GET)
    List<NotificationDto> getAllNotifications();

    // Delete notification
    void deleteNotification(Long id);
}
