package com.defence.portal.notification.controller;

import com.defence.portal.notification.dto.NotificationDto;
import com.defence.portal.notification.entity.Notification;
import com.defence.portal.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/notifications")
@CrossOrigin(origins = "*")
public class AdminNotificationController {

    private final NotificationService notificationService;

    public AdminNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /* ===== CREATE NOTIFICATION ===== */
    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @Valid @RequestBody NotificationDto notificationDto) {

        Notification saved = notificationService.createNotification(notificationDto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /* ===== UPDATE NOTIFICATION ===== */
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationDto notificationDto) {

        Notification updated = notificationService.updateNotification(id, notificationDto);

        return ResponseEntity.ok(updated);
    }

    /* ===== DELETE NOTIFICATION ===== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
