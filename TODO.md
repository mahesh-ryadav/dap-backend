# Integration Plan: Frontend-Backend Integration for Defence Aspirant Portal

## Current Status
- [x] Analyzed frontend and backend structures
- [x] Identified integration gaps (exam system missing in frontend)
- [x] Got user approval for plan

## Tasks to Complete

### 1. Update API Service
- [ ] Add exam-related API functions to `dap frontend/src/services/api.js`
  - Mock test functions (getMockTests, getPublishedMockTests, getMockTestById, createMockTest, updateMockTest, deleteMockTest, publishMockTest)
  - Question functions (getQuestions, getQuestionById, createQuestion, updateQuestion, deleteQuestion, getQuestionsBySection)
  - Test attempt functions (startTestAttempt, submitTestAttempt, getTestAttempts, getTestAttemptById, getAttemptsByUser, getAttemptsByMockTest, deleteTestAttempt)

### 2. Create Exam Pages
- [ ] Create `dap frontend/src/pages/MockTestList.jsx` - List all published mock tests
- [ ] Create `dap frontend/src/pages/MockTestDetail.jsx` - Show test details and start attempt
- [ ] Create `dap frontend/src/pages/TakeTest.jsx` - Test taking interface with timer and questions
- [ ] Create `dap frontend/src/pages/TestResults.jsx` - Show test results and score

### 3. Update Routing
- [ ] Update `dap frontend/src/App.jsx` to add exam routes with ProtectedRoute
  - /mock-tests -> MockTestList
  - /mock-tests/:id -> MockTestDetail
  - /take-test/:attemptId -> TakeTest
  - /test-results/:attemptId -> TestResults

### 4. Update Components
- [ ] Update `dap frontend/src/pages/Home.jsx` to add "Take Mock Tests" link/button
- [ ] Update `dap frontend/src/components/Navbar.jsx` to add exam navigation links

### 5. Test Integration
- [ ] Start backend server (`mvn spring-boot:run` in portal folder)
- [ ] Start frontend server (`npm run dev` in dap frontend folder)
- [ ] Test authentication flow
- [ ] Test exam listing and taking flow
- [ ] Verify API calls work correctly

## Notes
- Backend CORS is already configured for frontend dev server
- Frontend proxy is set up for backend API calls
- JWT authentication is handled in both ends
