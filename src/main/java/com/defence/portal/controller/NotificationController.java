package com.defence.portal.controller;

import com.defence.portal.dto.NotificationDto;
import com.defence.portal.entity.Notification;
import com.defence.portal.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    /* ===== CREATE NOTIFICATION (ADMIN) ===== */
    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @Valid @RequestBody NotificationDto notificationDto) {

        Notification saved =
                notificationService.createNotification(notificationDto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /* ===== UPDATE NOTIFICATION (ADMIN) ===== */
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationDto notificationDto) {

        Notification updated =
                notificationService.updateNotification(id, notificationDto);

        return ResponseEntity.ok(updated);
    }

    /* ===== GET SINGLE NOTIFICATION (DTO) ===== */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(
            @PathVariable Long id) {

        NotificationDto notification =
                notificationService.getNotificationById(id);

        return ResponseEntity.ok(notification);
    }

    /* ===== GET ALL NOTIFICATIONS (DTO) ===== */
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {

        List<NotificationDto> notifications =
                notificationService.getAllNotifications();

        return ResponseEntity.ok(notifications);
    }

    /* ===== DELETE NOTIFICATION (ADMIN) ===== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
