package com.defence.portal.service.impl;

import com.defence.portal.dto.NotificationDto;
import com.defence.portal.entity.Notification;
import com.defence.portal.exception.ResourceNotFoundException;
import com.defence.portal.repository.NotificationRepository;
import com.defence.portal.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository repository,
                                   ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    /* ===== CREATE ===== */
    @Override
    public Notification createNotification(NotificationDto dto) {
        Notification notification =
                modelMapper.map(dto, Notification.class);
        return repository.save(notification);
    }

    /* ===== UPDATE ===== */
    @Override
    @Transactional
    public Notification updateNotification(Long id, NotificationDto dto) {
        Notification existing = getNotificationEntityById(id);

        modelMapper.map(dto, existing);

        return repository.save(existing);
    }


    /* ===== GET BY ID (DTO) ===== */
    @Override
    @Transactional(readOnly = true)
    public NotificationDto getNotificationById(Long id) {
        Notification notification = getNotificationEntityById(id);
        return modelMapper.map(notification, NotificationDto.class);
    }

    /* ===== GET ALL (DTO) ===== */
    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotifications() {
        return repository.findAll()
                .stream()
                .map(notification ->
                        modelMapper.map(notification, NotificationDto.class))
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found with id " + id));

    }
}
