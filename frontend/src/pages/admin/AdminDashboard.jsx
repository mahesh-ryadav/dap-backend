import {
  Users,
  FileText,
  PlusCircle,
  Activity,
  Bell,
  ArrowUpRight
} from "lucide-react";

import AdminSidebar from "../../components/admin/AdminSidebar";
import StatCard from "../../components/admin/StatCard";
import { Link } from "react-router-dom";

const AdminDashboard = () => {

  const stats = [
    { title: "Total Users", value: "1,284", icon: <Users size={24} />, trend: "up", trendValue: "12", color: "blue" },
    { title: "Total Exams", value: "24", icon: <FileText size={24} />, trend: "up", trendValue: "8", color: "blue" },
    { title: "Active Attempts", value: "156", icon: <Activity size={24} />, trend: "up", trendValue: "24", color: "blue" },
    { title: "Notifications", value: "48", icon: <Bell size={24} />, trend: "down", trendValue: "2", color: "blue" },
  ];

  const recentActivity = [
    { id: 1, type: "exam", action: "New exam created", details: "NDA 2024 Mock Test 1", time: "2 hours ago" },
    { id: 2, type: "user", action: "User registered", details: "rahul_def@email.com", time: "3 hours ago" },
    { id: 3, type: "notification", action: "Notification sent", details: "AFCAT Admit Card Update", time: "5 hours ago" },
  ];

  return (

    <div className="flex min-h-screen bg-gray-100">

      <AdminSidebar />

      <main className="flex-1 p-8 overflow-y-auto">

        <div className="max-w-7xl mx-auto">

          {/* Header */}

          <header className="flex justify-between items-center mb-8">

            <div>
              <h1 className="text-3xl font-semibold text-gray-900">
                Dashboard Overview
              </h1>

              <p className="text-gray-500 mt-1">
                Welcome back, Admin. Here's what's happening today.
              </p>
            </div>

            <Link
              to="/admin/exams/new"
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2.5 rounded-lg font-medium flex items-center gap-2 transition shadow-sm"
            >
              <PlusCircle size={18} />
              Create New Exam
            </Link>

          </header>


          {/* Stats */}

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">

            {stats.map((stat, index) => (
              <StatCard key={index} {...stat} />
            ))}

          </div>


          {/* Main Section */}

          <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">


            {/* Recent Activity */}

            <div className="lg:col-span-2 bg-white border border-gray-200 rounded-xl p-6 shadow-sm">

              <div className="flex justify-between items-center mb-6">

                <h2 className="text-lg font-semibold text-gray-900">
                  Recent Activity
                </h2>

                <button className="text-blue-600 text-sm hover:underline">
                  View All
                </button>

              </div>

              <div className="space-y-4">

                {recentActivity.map((activity) => (

                  <div
                    key={activity.id}
                    className="flex items-center gap-4 p-4 rounded-lg bg-gray-50 border border-gray-200"
                  >

                    <div className="p-2 bg-blue-100 rounded-lg text-blue-600">

                      {activity.type === "exam" && <FileText size={18} />}
                      {activity.type === "user" && <Users size={18} />}
                      {activity.type === "notification" && <Bell size={18} />}

                    </div>

                    <div className="flex-1">

                      <p className="text-gray-900 font-medium">
                        {activity.action}
                      </p>

                      <p className="text-gray-500 text-sm">
                        {activity.details}
                      </p>

                    </div>

                    <span className="text-gray-400 text-sm">
                      {activity.time}
                    </span>

                  </div>

                ))}

              </div>

            </div>


            {/* Quick Actions */}

            <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm">

              <h2 className="text-lg font-semibold text-gray-900 mb-6">
                Quick Actions
              </h2>

              <div className="space-y-3">

                {[
                  { title: "Manage Users", path: "/admin/users", icon: <Users size={18} /> },
                  { title: "Exam Reports", path: "/admin/reports", icon: <Activity size={18} /> },
                  { title: "Portal Settings", path: "/admin/settings", icon: <Settings size={18} /> },
                ].map((link, idx) => (

                  <Link
                    key={idx}
                    to={link.path}
                    className="flex items-center justify-between p-4 rounded-lg bg-gray-50 border border-gray-200 hover:bg-gray-100 transition group"
                  >

                    <div className="flex items-center gap-3 text-gray-700">

                      {link.icon}

                      <span className="font-medium">
                        {link.title}
                      </span>

                    </div>

                    <ArrowUpRight
                      size={18}
                      className="text-gray-400 group-hover:text-blue-600 transition"
                    />

                  </Link>

                ))}

              </div>

            </div>

          </div>

        </div>

      </main>

    </div>

  );
};


const Settings = ({ size }) => (
  <svg
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <circle cx="12" cy="12" r="3" />
  </svg>
);

export default AdminDashboard;