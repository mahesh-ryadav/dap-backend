import apiClient from '../apiClient';

const ADMIN_NOTIFICATION_API = '/admin/notifications';

export const createNotification = (notificationData) => {
    return apiClient.post(ADMIN_NOTIFICATION_API, notificationData);
};

export const updateNotification = (id, notificationData) => {
    return apiClient.put(`${ADMIN_NOTIFICATION_API}/${id}`, notificationData);
};

export const deleteNotification = (id) => {
    return apiClient.delete(`${ADMIN_NOTIFICATION_API}/${id}`);
};

const adminNotificationService = {
    createNotification,
    updateNotification,
    deleteNotification,
};

export default adminNotificationService;
