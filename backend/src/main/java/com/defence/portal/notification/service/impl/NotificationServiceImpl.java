package com.defence.portal.notification.service.impl;

import com.defence.portal.notification.dto.NotificationDto;
import com.defence.portal.notification.entity.Notification;
import com.defence.portal.exception.ResourceNotFoundException;
import com.defence.portal.notification.repository.NotificationRepository;
import com.defence.portal.notification.service.NotificationService;
import com.defence.portal.notification.mapper.NotificationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationRepository repository,
            NotificationMapper notificationMapper) {
        this.repository = repository;
        this.notificationMapper = notificationMapper;
    }

    /* ===== CREATE ===== */
    @Override
    public Notification createNotification(NotificationDto dto) {
        Notification notification = notificationMapper.toEntity(dto);
        return repository.save(notification);
    }

    /* ===== UPDATE ===== */
    @Override
    @Transactional
    public Notification updateNotification(Long id, NotificationDto dto) {
        Notification existing = getNotificationEntityById(id);
        notificationMapper.updateFromDto(dto, existing);
        return repository.save(existing);
    }

    /* ===== GET BY ID (DTO) ===== */
    @Override
    @Transactional(readOnly = true)
    public NotificationDto getNotificationById(Long id) {
        Notification notification = getNotificationEntityById(id);
        return notificationMapper.toDto(notification);
    }

    /* ===== GET ALL (DTO) ===== */
    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotifications() {
        return repository.findAll()
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    /* ===== DELETE ===== */
    @Override
    public void deleteNotification(Long id) {
        Notification notification = getNotificationEntityById(id);
        repository.delete(notification);
    }

    /* ===== INTERNAL HELPER ===== */
    private Notification getNotificationEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

    }
}
