# Fix Login CORS and API URL Issue

## Steps:
- [x] 1. Create/update frontend/.env with VITE_API_BASE_URL
- [x] 2. Update frontend/src/services/apiClient.js to use VITE_API_BASE_URL
- [x] 3. Create backend/src/main/java/com/defence/portal/config/CorsConfig.java
- [x] 4. Update backend/src/main/java/com/defence/portal/auth/config/SecurityConfig.java to use CORS config
- [x] 5. Test locally (restart frontend/backend)
- [x] 6. Deploy and verify (Vercel auto, Render rebuild)

✅ Task complete!

Progress will be updated after each step.

