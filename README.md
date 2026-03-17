# Defence Aspirant Portal 🛡️

[![Frontend](https://img.shields.io/website?down_message=offline&label=Frontend&url=https%3A%2F%2Fdefence-aspirant-portal.vercel.app)](https://defence-aspirant-portal.vercel.app)
[![Backend](https://img.shields.io/website?down_message=offline&label=Backend&url=https%3A%2F%2Fdefence-aspirant-portal.onrender.com)](https://defence-aspirant-portal.onrender.com)

The **Defence Aspirant Portal** is a comprehensive web platform designed for defence exam aspirants. Practice mock tests, view latest notifications, access study materials, and track your preparation progress.

## ✨ Live Demo

- **Frontend**: [https://defence-aspirant-portal.vercel.app](https://defence-aspirant-portal.vercel.app)
- **Backend API**: [https://defence-aspirant-portal.onrender.com](https://defence-aspirant-portal.onrender.com)
- **Swagger API Docs**: [https://defence-aspirant-portal.onrender.com/swagger-ui.html](https://defence-aspirant-portal.onrender.com/swagger-ui.html)

**Demo Credentials:**
```
Username: demo@example.com
Password: password123
```

## 🚀 Features

- **Mock Test System**: Timed exams with MCQs, auto-evaluation, results
- **JWT Authentication**: Secure login/register with role-based access
- **Notifications**: Real-time exam updates and announcements
- **Study Materials**: Upload/download categorized PDFs/documents
- **Admin Dashboard**: Create exams, manage questions/options, notifications
- **Responsive UI**: Modern React interface optimized for all devices
- **File Upload**: Secure study material management

## 🛠 Tech Stack

| Component | Technology |
|-----------|------------|
| **Frontend** | React 18 + Vite + Tailwind CSS + React Router |
| **Backend** | Spring Boot 3 + Java 17 + Spring Security (JWT) |
| **Database** | PostgreSQL |
| **Authentication** | JWT + Spring Security |
| **File Storage** | Local storage with streaming download |
| **API Docs** | OpenAPI/Swagger |
| **Frontend Deploy** | Vercel |
| **Backend Deploy** | Render (Docker) |

## 🏗 System Architecture

```
Frontend (React + Vite) ─── HTTPS ─── Vercel ─── API Calls ─── Backend (Spring Boot)
                                                              │
                                                              ├── PostgreSQL (Render)
                                                              └── File Storage
```

- **Stateless JWT Auth**: Token-based, no sessions
- **RESTful APIs**: Clean endpoints with DTOs
- **Role-based Access**: USER/ADMIN roles
- **CORS Enabled**: Cross-origin requests from Vercel + localhost

## 📁 Project Structure

```
Defence-Aspirant-Portal/
├── frontend/                 # React + Vite app
│   ├── src/
│   │   ├── components/       # Reusable UI components
│   │   ├── services/         # API clients
│   │   ├── pages/            # Page components
│   │   └── contexts/         # Auth context
│   ├── .env                  # Vite env vars
│   └── package.json
│
└── backend/                  # Spring Boot API
    ├── src/main/java/com/defence/portal/
    │   ├── auth/             # Authentication & JWT
    │   ├── examportal/       # Mock test logic
    │   ├── notification/     # Notifications
    │   ├── studymaterials/   # File management
    │   └── config/           # CORS, Security, OpenAPI
    ├── src/main/resources/
    │   └── application.properties
    ├── pom.xml              # Maven dependencies
    └── Dockerfile           # Render deployment;
```

## 📚 Backend API Documentation

### Authentication APIs

**`POST /api/auth/login`**
> Authenticate user and return JWT token

**Request:**
```json
{
  \"username\": \"demo@example.com\",
  \"password\": \"password123\"
}
```

**Response:**
```json
{
  \"token\": \"eyJhbGciOiJIUzI1NiJ9...token...\"
}
```

**`POST /api/auth/register`**
> Register new user

**Request:**
```json
{
  \"username\": \"newuser\",
  \"email\": \"new@example.com\",
  \"password\": \"securepass123\"
}
```

**Response:** `\"User registered successfully!\"`

**`GET /api/auth/profile`**
> Get current user profile (JWT required)

**Response:**
```json
{
  \"id\": 1,
  \"username\": \"demo\",
  \"email\": \"demo@example.com\",
  \"roles\": [\"ROLE_USER\"]
}
```

### Exam APIs (Public + Authenticated)

**`GET /api/exams`**
> Get all active exams

**Response:** `Array<ExamDTO>`

**`GET /api/exams/{examId}`**
> Get exam details

**`POST /api/exams/{examId}/start`**
> Start exam (returns attempt ID)

**`GET /api/attempts/{attemptId}/questions`**
> Get questions for attempt

**`POST /api/attempts/{attemptId}/answers`**
> Submit answer

**Request:**
```json
{
  \"questionId\": 1,
  \"selectedOptionKey\": \"A\"
}
```

**`POST /api/attempts/{attemptId}/submit`**
> Submit exam for evaluation

**Response:** `ExamResultDTO`

**`GET /api/attempts/{attemptId}/result`**
> Get exam results

### Admin Exam APIs (ADMIN role)

**`POST /api/admin/exams`**
> Create exam

**Request:** `CreateExamRequestDTO`

**`POST /api/admin/exams/{examId}/questions`**
> Add question to exam

**`POST /api/admin/questions/{questionId}/options`**
> Add option to question

**`PUT/PATCH /api/admin/exams/{examId}`**
> Update exam

**`PUT/PATCH /api/admin/questions/{questionId}`**
> Update question

**`PUT/PATCH /api/admin/options/{optionId}`**
> Update option

### Notification APIs

**`GET /api/notifications`**
> Get all notifications

**`GET /api/notifications/{id}`**
> Get single notification

### Admin Notification APIs (ADMIN)

**`POST /api/admin/notifications`**
> Create notification

**Request:** `NotificationDto`

**`PUT /api/admin/notifications/{id}`**
> Update notification

**`DELETE /api/admin/notifications/{id}`**
> Delete notification

### Study Material APIs

**`GET /api/study-material`**
> Get all study materials

**`GET /api/study-material/category/{categoryId}`**
> Get materials by category

**`GET /api/study-material/download/{id}`**
> Download file

### Admin Study Material APIs

**`POST /api/admin/study-material/upload`**
> Upload file (multipart/form-data)

**Params:** `file` (MultipartFile), `categoryId` (Long)

**`DELETE /api/admin/study-material/{id}`**
> Delete file

### Admin Category APIs

**`POST /api/admin/category`**
> Create category

**Request:** `Category { name: \"NDA\" }`

**`DELETE /api/admin/category/{id}`**
> Delete category

**`GET /api/category`**
> Get all categories

**`GET /api/category/{id}`**
> Get category by ID

## 🔧 Environment Variables (Backend)

| Variable | Description | Example |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection | `jdbc:postgresql://host:port/db` |
| `SPRING_DATASOURCE_USERNAME` | DB username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | DB password | `securepass` |
| `JWT_SECRET` | JWT signing key (32+ chars) | `mySecretKeyForJWT` |
| `JWT_EXPIRATION_MS` | Token expiry (ms) | `86400000` (24h) |

## 🏃‍♂️ Run Locally

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL
- Maven (backend)
- Docker (optional)

### Backend
```bash
cd backend
cp .env.example .env  # or set env vars
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## ☁️ Deployment

### Backend (Render)
1. Push Docker image or connect Git repo
2. Set environment variables
3. Build Command: `./mvnw clean package -DskipTests`
4. Start Command: `java -jar target/*.jar`

### Frontend (Vercel)
1. Connect GitHub repo
2. Framework: Vite
3. Env Var: `VITE_API_BASE_URL=https://defence-aspirant-portal.onrender.com/api`
4. Deploy auto on push

## 📸 Screenshots

<!-- Add screenshots here -->
* [Home/Dashboard](#)
* [Mock Test](#)
* [Admin Panel](#)
* [Study Materials](#)

## 🔮 Future Improvements

- [ ] Real-time notifications (WebSocket)
- [ ] Exam analytics dashboard
- [ ] User progress tracking
- [ ] Payment integration
- [ ] Mobile app (React Native)
- [ ] AI question generator
- [ ] Cloud storage (AWS S3)

## 👨‍💻 Author

**Mahesh Ryadav**
- GitHub: [github.com/mahesh-ryadav](https://github.com/mahesh-ryadav)
- LinkedIn: [linkedin.com/in/mahesh-ryadav](https://linkedin.com/in/mahesh-ryadav)

## 📄 License

This project is developed and maintained by Mahesh Yadav as part of a full-stack portfolio demonstrating React, Spring Boot, and PostgreSQL.
