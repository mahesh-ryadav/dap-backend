import apiClient from './apiClient';

const NOTIFICATION_API = '/notifications';

export const getNotifications = () => {
    return apiClient.get(NOTIFICATION_API);
};

export const getNotificationById = (id) => {
    return apiClient.get(`${NOTIFICATION_API}/${id}`);
};

const notificationService = {
    getNotifications,
    getNotificationById,
};

export default notificationService;
