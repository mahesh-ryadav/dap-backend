import { Link } from "react-router-dom";

const NotificationCard = ({ notification }) => {
  if (!notification) return null;

  return (
    <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition flex flex-col">

      {/* Header */}

      <div className="flex justify-between items-center mb-3">

        <span className="text-xs font-medium px-3 py-1 rounded-full bg-blue-100 text-blue-600">
          {notification.notificationType?.replaceAll("_", " ") || "Notification"}
        </span>

        <span className="text-xs text-gray-500">
          {notification.notificationDate || "No Date"}
        </span>

      </div>


      {/* Exam Name */}

      <h3 className="text-lg font-semibold text-gray-900 mb-2">
        {notification.examName || "No Exam Name"}
      </h3>


      {/* Overview */}

      <p className="text-sm text-gray-500 line-clamp-2 mb-4">
        {notification.overview || "No overview available"}
      </p>


      {/* Result Date */}

      <div className="text-sm mb-4">

        <span className="font-medium text-gray-700">
          Result Date:
        </span>

        <span className="text-gray-600 ml-1">
          {notification.resultDate || "Coming Soon"}
        </span>

      </div>


      {/* Button */}

      <div className="flex justify-end mt-auto">

        <Link
          to={`/notifications/${notification.id}`}
          className="bg-blue-600 hover:bg-blue-700 text-white text-sm px-4 py-2 rounded-lg transition"
        >
          View Details
        </Link>

      </div>

    </div>
  );
};

export default NotificationCard;