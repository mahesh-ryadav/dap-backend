import { useState, useEffect } from "react";
import {
    Plus,
    Search,
    Edit2,
    Trash2,
    Bell,
    Calendar,
    AlertCircle
} from "lucide-react";
import AdminSidebar from "../../components/admin/AdminSidebar";
import notificationService from "../../services/notification.service";
import adminNotificationService from "../../services/admin/adminNotification.service";
import { motion, AnimatePresence } from "framer-motion";

const AdminNotificationList = () => {
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        fetchNotifications();
    }, []);

    const fetchNotifications = async () => {
        try {
            const response = await notificationService.getNotifications();
            setNotifications(response.data);
        } catch (error) {
            console.error("Error fetching notifications:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this notification?")) {
            try {
                await adminNotificationService.deleteNotification(id);
                setNotifications(notifications.filter(n => n.id !== id));
            } catch (error) {
                console.error("Error deleting notification:", error);
                alert("Failed to delete notification.");
            }
        }
    };

    const filteredNotifications = notifications.filter(n =>
        n.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        n.content.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="flex min-h-screen bg-slate-950">
            <AdminSidebar />

            <main className="flex-1 p-8">
                <div className="max-w-7xl mx-auto">
                    <header className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
                        <div>
                            <h1 className="text-3xl font-bold text-white">Notifications</h1>
                            <p className="text-slate-400 mt-1">Manage portal announcements and alerts.</p>
                        </div>
                        <button
                            className="bg-yellow-500 hover:bg-yellow-400 text-slate-900 px-6 py-2.5 rounded-xl font-bold flex items-center gap-2 transition-all shadow-lg shadow-yellow-500/20 w-fit"
                        >
                            <Plus size={20} />
                            New Notification
                        </button>
                    </header>

                    <div className="bg-slate-900/50 border border-slate-800 rounded-2xl overflow-hidden">
                        {/* Controls */}
                        <div className="p-4 border-b border-slate-800 flex flex-col md:flex-row gap-4 justify-between items-center bg-slate-900/80">
                            <div className="relative w-full md:w-96">
                                <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" size={18} />
                                <input
                                    type="text"
                                    placeholder="Search notifications..."
                                    className="w-full bg-slate-800 border border-slate-700 rounded-lg py-2 pl-10 pr-4 text-white focus:outline-none focus:border-yellow-500 transition-colors"
                                    value={searchTerm}
                                    onChange={(e) => setSearchTerm(e.target.value)}
                                />
                            </div>
                        </div>

                        {/* List */}
                        <div className="p-6">
                            <div className="space-y-4">
                                <AnimatePresence>
                                    {loading ? (
                                        <div className="flex justify-center py-12">
                                            <div className="w-8 h-8 border-2 border-yellow-500 border-t-transparent rounded-full animate-spin"></div>
                                        </div>
                                    ) : filteredNotifications.length === 0 ? (
                                        <div className="text-center py-12 text-slate-500">
                                            No notifications found.
                                        </div>
                                    ) : (
                                        filteredNotifications.map((notification) => (
                                            <motion.div
                                                key={notification.id}
                                                initial={{ opacity: 0, y: 10 }}
                                                animate={{ opacity: 1, y: 0 }}
                                                exit={{ opacity: 0, scale: 0.95 }}
                                                className="bg-slate-800/40 border border-slate-700/50 rounded-xl p-5 hover:border-slate-600 transition-all group relative"
                                            >
                                                <div className="flex items-start gap-4">
                                                    <div className="p-3 bg-yellow-500/10 rounded-lg text-yellow-500 mt-1">
                                                        <Bell size={20} />
                                                    </div>
                                                    <div className="flex-1 pr-12">
                                                        <h3 className="text-white font-semibold text-lg mb-1">{notification.title}</h3>
                                                        <p className="text-slate-400 line-clamp-2 text-sm mb-3">{notification.content}</p>
                                                        <div className="flex items-center gap-4 text-xs text-slate-500">
                                                            <span className="flex items-center gap-1.5">
                                                                <Calendar size={14} />
                                                                {new Date(notification.createdAt).toLocaleDateString()}
                                                            </span>
                                                            {notification.priority === 'HIGH' && (
                                                                <span className="flex items-center gap-1.5 text-red-500">
                                                                    <AlertCircle size={14} />
                                                                    High Priority
                                                                </span>
                                                            )}
                                                        </div>
                                                    </div>

                                                    <div className="absolute top-5 right-5 flex items-center gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                                                        <button
                                                            className="p-2 text-slate-400 hover:text-blue-500 hover:bg-slate-700 rounded-lg transition-all"
                                                            title="Edit"
                                                        >
                                                            <Edit2 size={18} />
                                                        </button>
                                                        <button
                                                            onClick={() => handleDelete(notification.id)}
                                                            className="p-2 text-slate-400 hover:text-red-500 hover:bg-slate-700 rounded-lg transition-all"
                                                            title="Delete"
                                                        >
                                                            <Trash2 size={18} />
                                                        </button>
                                                    </div>
                                                </div>
                                            </motion.div>
                                        ))
                                    )}
                                </AnimatePresence>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default AdminNotificationList;
