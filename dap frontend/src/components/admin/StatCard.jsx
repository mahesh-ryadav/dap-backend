const StatCard = ({ title, value, icon, trend, trendValue, color }) => {

  const colorClasses = {
    yellow: "bg-yellow-100 text-yellow-600",
    blue: "bg-blue-100 text-blue-600",
    green: "bg-green-100 text-green-600",
    purple: "bg-purple-100 text-purple-600",
    red: "bg-red-100 text-red-600",
  };

  return (

    <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">

      {/* Top Section */}

      <div className="flex justify-between items-start mb-4">

        <div
          className={`p-3 rounded-lg ${
            colorClasses[color] || colorClasses.blue
          }`}
        >
          {icon}
        </div>

        {trend && (

          <span
            className={`text-xs font-medium px-2 py-1 rounded-full
              
              ${
                trend === "up"
                  ? "bg-green-100 text-green-600"
                  : "bg-red-100 text-red-600"
              }
              
            `}
          >
            {trend === "up" ? "+" : "-"}
            {trendValue}%
          </span>

        )}

      </div>


      {/* Content */}

      <div>

        <h3 className="text-gray-500 text-sm font-medium mb-1">
          {title}
        </h3>

        <p className="text-2xl font-semibold text-gray-900">
          {value}
        </p>

      </div>

    </div>

  );
};

export default StatCard;