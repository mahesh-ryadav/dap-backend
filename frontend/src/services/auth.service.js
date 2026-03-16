import apiClient from './apiClient';

const AUTH_API = '/auth';

export const login = (credentials) => {
    return apiClient.post(`${AUTH_API}/login`, credentials);
};

export const register = (userData) => {
    return apiClient.post(`${AUTH_API}/register`, userData);
};

export const getCurrentUserProfile = () => {
    return apiClient.get(`${AUTH_API}/profile`);
};

const authService = {
    login,
    register,
    getCurrentUserProfile,
};

export default authService;
