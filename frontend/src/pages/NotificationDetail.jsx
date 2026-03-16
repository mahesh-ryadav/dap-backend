import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import notificationService from "../services/notification.service";

const NotificationDetail = () => {
  const { id } = useParams();

  const [notification, setNotification] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchNotification = async () => {
      if (!id) return;

      try {
        const res = await notificationService.getNotificationById(id);
        setNotification(res.data);
      } catch (err) {
        setError("Notification not found");
      } finally {
        setLoading(false);
      }
    };

    fetchNotification();
  }, [id]);

  if (loading)
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-900 text-white">
        Loading...
      </div>
    );

  if (error)
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-900 text-white">
        {error}
      </div>
    );

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-7xl mx-auto px-6 py-8">
        {/* HEADER */}

        <div className="mb-8">
          <h1 className="text-3xl font-semibold text-gray-900">
            {notification.examName}
          </h1>

          <p className="text-gray-500 mt-2">
            {notification.conductingAuthority}
          </p>
        </div>

        {/* ACTION BUTTONS */}

        <div className="flex gap-4 mb-8">
          {notification.applyOnlineLink && (
            <a
              href={notification.applyOnlineLink}
              target="_blank"
              className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg font-medium"
            >
              Apply Online
            </a>
          )}

          {notification.officialNotificationPdf && (
            <a
              href={notification.officialNotificationPdf}
              target="_blank"
              className="border border-gray-300 px-5 py-2 rounded-lg font-medium hover:bg-gray-50"
            >
              Download PDF
            </a>
          )}

          {notification.officialWebsite && (
            <a
              href={notification.officialWebsite}
              target="_blank"
              className="border border-gray-300 px-5 py-2 rounded-lg font-medium hover:bg-gray-50"
            >
              Official Website
            </a>
          )}
        </div>

        {/* OVERVIEW */}

        <div className="bg-white border border-gray-200 rounded-xl p-6 mb-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-3">Overview</h2>

          <p className="text-gray-600 leading-relaxed">
            {notification.overview}
          </p>
        </div>

        {/* GRID SECTION */}

        <div className="grid md:grid-cols-2 gap-6 mb-6">
          {/* IMPORTANT DATES */}

          <div className="bg-white border border-gray-200 rounded-xl p-6">
            <h3 className="font-semibold text-gray-900 mb-4">
              Important Dates
            </h3>

            <ul className="space-y-2 text-gray-600">
              <li>Application Start: {notification.applicationStartDate}</li>
              <li>Application End: {notification.applicationEndDate}</li>
              <li>Exam Date: {notification.examDate}</li>
              <li>Admit Card Date: {notification.admitCardDate}</li>
              <li>Result Date: {notification.resultDate}</li>
            </ul>
          </div>

          {/* ELIGIBILITY */}

          <div className="bg-white border border-gray-200 rounded-xl p-6">
            <h3 className="font-semibold text-gray-900 mb-4">Eligibility</h3>

            <p className="text-gray-600 mb-2">
              <b>Education:</b> {notification.educationalQualification}
            </p>

            <p className="text-gray-600 mb-2">
              <b>Age:</b> {notification.minAge} - {notification.maxAge}
            </p>

            <p className="text-gray-600">
              <b>Nationality:</b> {notification.nationality}
            </p>
          </div>
        </div>

        {/* VACANCY + FEE */}

        <div className="grid md:grid-cols-2 gap-6 mb-6">
          <div className="bg-white border border-gray-200 rounded-xl p-6">
            <h3 className="font-semibold text-gray-900 mb-4">Vacancies</h3>

            <p className="text-2xl font-semibold text-gray-900">
              {notification.totalVacancies}
            </p>

            <p className="text-gray-500 mt-2">{notification.vacancyDetails}</p>
          </div>

          <div className="bg-white border border-gray-200 rounded-xl p-6">
            <h3 className="font-semibold text-gray-900 mb-4">
              Application Fee
            </h3>

            <p className="text-gray-600">General: ₹{notification.feeGeneral}</p>

            <p className="text-gray-600">SC/ST: ₹{notification.feeScSt}</p>

            <p className="text-gray-500 mt-2">
              Payment Mode: {notification.paymentMode}
            </p>
          </div>
        </div>

        {/* SELECTION PROCESS */}

        {notification.selectionProcess?.length > 0 && (
          <div className="bg-white border border-gray-200 rounded-xl p-6 mb-6">
            <h3 className="font-semibold text-gray-900 mb-4">
              Selection Process
            </h3>

            <ul className="space-y-2 text-gray-600">
              {notification.selectionProcess.map((step, index) => (
                <li key={index}>• {step}</li>
              ))}
            </ul>
          </div>
        )}

        {/* SYLLABUS */}

        {notification.syllabus?.length > 0 && (
          <div className="bg-white border border-gray-200 rounded-xl p-6">
            <h3 className="font-semibold text-gray-900 mb-4">Syllabus</h3>

            <div className="grid md:grid-cols-3 gap-4">
              {notification.syllabus.map((topic, index) => (
                <div
                  key={index}
                  className="border border-gray-200 rounded-lg p-3 text-gray-700"
                >
                  {topic}
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default NotificationDetail;
