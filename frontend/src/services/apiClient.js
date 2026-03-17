import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    withCredentials: true,
});

// Add JWT token to requests if available
apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Add response interceptor to handle 401 errors (token expired)
apiClient.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            // Token expired or invalid, clear localStorage and redirect to login
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            localStorage.removeItem('roles');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default apiClient;
