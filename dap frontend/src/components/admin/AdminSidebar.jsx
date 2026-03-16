import { Link, useLocation } from "react-router-dom";
import {
  LayoutDashboard,
  FileText,
  Bell,
  Settings,
  ChevronRight
} from "lucide-react";

const AdminSidebar = () => {
  const location = useLocation();

  const menuItems = [
    {
      title: "Dashboard",
      path: "/admin/dashboard",
      icon: <LayoutDashboard size={20} />,
    },
    {
      title: "Manage Exams",
      path: "/admin/exams",
      icon: <FileText size={20} />,
    },
    {
      title: "Notifications",
      path: "/admin/notifications",
      icon: <Bell size={20} />,
    },
  ];

  return (
    <div className="w-64 bg-white min-h-screen flex flex-col border-r border-gray-200">

      {/* Logo */}

      <div className="p-6 border-b border-gray-200">
        <h2 className="text-xl font-semibold text-gray-900">
          Admin Panel
        </h2>
      </div>


      {/* Menu */}

      <nav className="flex-1 px-4 py-4">

        <ul className="space-y-2">

          {menuItems.map((item) => {

            const isActive = location.pathname === item.path;

            return (
              <li key={item.path}>

                <Link
                  to={item.path}
                  className={`flex items-center justify-between px-4 py-3 rounded-lg transition 
                  
                  ${isActive
                      ? "bg-blue-100 text-blue-600 font-medium"
                      : "text-gray-600 hover:bg-gray-100 hover:text-blue-600"
                    }
                  
                  `}
                >

                  <div className="flex items-center gap-3">
                    {item.icon}
                    <span>{item.title}</span>
                  </div>

                  {isActive && (
                    <ChevronRight size={16} className="text-blue-600" />
                  )}

                </Link>

              </li>
            );

          })}

        </ul>

      </nav>


      {/* Footer */}

      <div className="p-4 border-t border-gray-200">

        <div className="flex items-center gap-3 px-4 py-2 text-gray-500 hover:text-blue-600 cursor-pointer">

          <Settings size={20} />
          <span className="text-sm">Settings</span>

        </div>

      </div>

    </div>
  );
};

export default AdminSidebar;