import { useState, useEffect } from "react";
import NotificationCard from "../components/NotificationCard";
import notificationService from "../services/notification.service";
import adminNotificationService from "../services/admin/adminNotification.service";
import { useAuth } from "../contexts/AuthContext";

const Notification = () => {

  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showCreateForm, setShowCreateForm] = useState(false);

  const [newNotification, setNewNotification] = useState({
    title: "",
    message: ""
  });

  const { user } = useAuth();

  useEffect(() => {

    const fetchNotifications = async () => {

      try {

        const response = await notificationService.getNotifications();
        const data = response.data;

        if (Array.isArray(data)) {
          setNotifications(data);
        } else {
          setError("Invalid data format received");
        }

      } catch (err) {

        setError(
          err.response?.data?.message ||
          err.message ||
          "Failed to fetch notifications"
        );

      } finally {
        setLoading(false);
      }

    };

    fetchNotifications();

  }, []);

  const handleCreate = async (e) => {

    e.preventDefault();

    try {

      await adminNotificationService.createNotification(newNotification);

      setNewNotification({
        title: "",
        message: ""
      });

      setShowCreateForm(false);
      setError(null);

      const response = await notificationService.getNotifications();

      if (Array.isArray(response.data)) {
        setNotifications(response.data);
      }

    } catch (err) {

      setError(
        err.response?.data?.message ||
        err.message ||
        "Failed to create notification"
      );

    }

  };

  /* LOADING */

  if (loading) {

    return (

      <div className="min-h-screen flex items-center justify-center bg-gray-100">

        <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm text-center">

          <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-blue-600 mx-auto mb-4"></div>

          <p className="text-gray-500">Loading notifications...</p>

        </div>

      </div>

    );

  }

  /* ERROR */

  if (error) {

    return (

      <div className="min-h-screen flex items-center justify-center bg-gray-100">

        <div className="bg-white border border-gray-200 rounded-xl p-10 shadow-sm text-center">

          <h2 className="text-xl font-semibold text-red-600 mb-2">
            Error Loading Notifications
          </h2>

          <p className="text-gray-500">{error}</p>

        </div>

      </div>

    );

  }

  return (

    <div className="min-h-screen bg-gray-100">

      {/* HEADER */}

      <div className="max-w-7xl mx-auto px-6 py-16 text-center">

        <h1 className="text-4xl font-semibold text-gray-900 mb-3">
          Defence Notifications
        </h1>

        <p className="text-gray-500 max-w-xl mx-auto">
          Stay updated with the latest defence exam schedules,
          results, and official announcements.
        </p>

      </div>


      {/* CONTENT */}

      <div className="max-w-7xl mx-auto px-6 pb-16">


        {/* ADMIN BUTTON */}

        {user?.roles?.includes("ROLE_ADMIN") && (

          <div className="mb-10 text-center">

            <button
              onClick={() => setShowCreateForm(!showCreateForm)}
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg"
            >
              {showCreateForm ? "Cancel" : "Create Notification"}
            </button>

          </div>

        )}


        {/* CREATE FORM */}

        {showCreateForm && (

          <div className="max-w-xl mx-auto mb-10 bg-white border border-gray-200 rounded-xl p-6 shadow-sm">

            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Create Notification
            </h3>

            <form onSubmit={handleCreate}>

              <input
                type="text"
                placeholder="Title"
                value={newNotification.title}
                onChange={(e) =>
                  setNewNotification({
                    ...newNotification,
                    title: e.target.value
                  })
                }
                className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-4"
                required
              />

              <textarea
                placeholder="Message"
                value={newNotification.message}
                onChange={(e) =>
                  setNewNotification({
                    ...newNotification,
                    message: e.target.value
                  })
                }
                className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-4"
                required
              />

              <button
                type="submit"
                className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg"
              >
                Create
              </button>

            </form>

          </div>

        )}


        {/* NOTIFICATION GRID */}

        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">

          {notifications?.length > 0 ? (

            notifications
              .filter(n => n?.id)
              .map((item) => (

                <NotificationCard
                  key={item.id}
                  notification={item}
                />

              ))

          ) : (

            <div className="col-span-full text-center py-16">

              <h3 className="text-lg text-gray-500">
                No Notifications Yet
              </h3>

            </div>

          )}

        </div>

      </div>

    </div>

  );

};

export default Notification;