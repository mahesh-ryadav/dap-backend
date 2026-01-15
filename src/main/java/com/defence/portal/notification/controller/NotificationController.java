package com.defence.portal.notification.controller;

import com.defence.portal.notification.dto.NotificationDto;

import com.defence.portal.notification.service.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /* ===== GET SINGLE NOTIFICATION (DTO) ===== */
    @GetMapping("/{id}")

    public ResponseEntity<NotificationDto> getNotificationById(
            @PathVariable Long id) {

        NotificationDto notification = notificationService.getNotificationById(id);

        return ResponseEntity.ok(notification);
    }

    /* ===== GET ALL NOTIFICATIONS (DTO) ===== */
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {

        List<NotificationDto> notifications = notificationService.getAllNotifications();

        return ResponseEntity.ok(notifications);
    }

}
