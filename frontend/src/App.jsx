import Navbar from "./components/Navbar.jsx";
import Home from "./pages/Home.jsx";
import Notification from "./pages/Notification.jsx";
import NotificationDetail from "./pages/NotificationDetail.jsx";
import Login from "./pages/Login.jsx";
import Signup from "./pages/Signup.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import MockTestDetail from "./pages/MockTestDetail.jsx";
import MockTestList from "./pages/MockTestList.jsx";
import TakeTest from "./pages/TakeTest.jsx";
import TestResults from "./pages/TestResults.jsx";
import AdminDashboard from "./pages/admin/AdminDashboard.jsx";
import AdminExamList from "./pages/admin/AdminExamList.jsx";
import AdminExamForm from "./pages/admin/AdminExamForm.jsx";
import AdminQuestionManager from "./pages/admin/AdminQuestionManager.jsx";
import AdminNotificationList from "./pages/admin/AdminNotificationList.jsx";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext.jsx";
import "./App.css";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />

        <Routes>
          {/* Public Routes */}

          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />

          <Route path="/notifications" element={<Notification />} />
          <Route path="/notifications/:id" element={<NotificationDetail />} />

          <Route path="/mock-tests" element={<MockTestList />} />

          {/* 👇 DETAILS PAGE SHOULD BE PUBLIC */}
          <Route path="/mock-tests/:id" element={<MockTestDetail />} />

          {/* Protected Test Routes */}

          <Route
            path="/take-test/:attemptId"
            element={
              <ProtectedRoute>
                <TakeTest />
              </ProtectedRoute>
            }
          />

          <Route
            path="/test-results/:attemptId"
            element={
              <ProtectedRoute>
                <TestResults />
              </ProtectedRoute>
            }
          />

          {/* Admin Routes */}

          <Route
            path="/admin/dashboard"
            element={
              <ProtectedRoute requiredRoles={["ROLE_ADMIN"]}>
                <AdminDashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/exams"
            element={
              <ProtectedRoute requiredRoles={["ROLE_ADMIN"]}>
                <AdminExamList />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/exams/new"
            element={
              <ProtectedRoute requiredRoles={["ROLE_ADMIN"]}>
                <AdminExamForm />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/exams/edit/:id"
            element={
              <ProtectedRoute requiredRoles={["ROLE_ADMIN"]}>
                <AdminExamForm />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/exams/:id/questions"
            element={
              <ProtectedRoute requiredRoles={["ROLE_ADMIN"]}>
                <AdminQuestionManager />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/notifications"
            element={
              <ProtectedRoute requiredRoles={["ROLE_ADMIN"]}>
                <AdminNotificationList />
              </ProtectedRoute>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
