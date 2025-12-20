# 🛡️ Defence Aspirant Portal – Backend

A backend-only Spring Boot application designed to power a defence exam preparation platform.
The system provides exam notifications, mock tests, results, admit card updates, and exam-related information through secure and scalable REST APIs.

This project follows industry-standard layered architecture and is built to be easily integrated with any frontend (React, Angular, Mobile apps).

## 🎯 Core Features
### 📢 Notification Management

Create defence exam notifications

Update exam status (Upcoming / Active / Closed)

Publish result announcements, exam dates, admit card releases

Store dynamic content like:

Selection process

Exam pattern

Syllabus

Physical standards

### 📝 Mock Test System (Currently Implemented)

**Mock Test Management:**
- ✅ Create mock tests for defence exams (BSF, SSC, NDA)
- ✅ Define test metadata (exam type, duration, total marks, negative marking)
- ✅ Publish/Draft status management
- ✅ Filter tests by exam type

**Question Bank:**
- ✅ Create MCQ and numeric questions
- ✅ Set difficulty levels (Easy, Medium, Hard)
- ✅ Link questions to sections
- ✅ Question CRUD operations

**Test Attempt System:**
- ✅ Start test attempts with time tracking
- ✅ Submit test attempts
- ✅ Track attempt status (In Progress, Submitted)
- ✅ User-specific attempt history

**Result & Scoring:**
- ✅ Basic result tracking
- ✅ Test attempt history
- ✅ Score storage

### 📚 Planned Features (Future Implementation)

**Section Management:**
- Organize tests into sections (Maths, GK, etc.)
- Define section-wise marks and question counts
- Hierarchical test structure

**Option Management:**
- Manage question options with correct answers
- Bulk option creation for questions

**Advanced Answer Tracking:**
- Individual answer submission
- Real-time answer validation
- Detailed performance analytics

### 📊 Result & Exam Updates

Result announcement notifications

Admit card release updates

Exam date announcements

"Coming Soon" handling for unreleased results

### 👤 User & Admin Ready

APIs designed with role-based access in mind

Admin APIs for create/update/delete

Public APIs for read-only access

Easily extensible with Spring Security

## 🧱 Backend Architecture

The application follows clean layered architecture:

Controller → Service → Repository → Database
DTO ↔ Entity mapping
Global Exception Handling

Why this architecture?

Loose coupling

High maintainability

Testability

Industry standard

## 🛠️ Tech Stack
### Backend

Java 21

Spring Boot

Spring Web (REST APIs)

Spring Data JPA

Hibernate

MySQL

ModelMapper

Jakarta Validation

Swagger (springdoc-openapi)

### Tools

Maven

IntelliJ IDEA

Postman

Git & GitHub

## 📂 Backend Project Structure
```
src/main/java/com/defence/portal
│
├── controller                    # REST controllers
│   ├── NotificationController.java
│   └── exam/                     # Exam system controllers
│       ├── MockTestController.java
│       ├── QuestionController.java
│       └── TestAttemptController.java
├── service                       # Business logic services
│   ├── NotificationService.java
│   └── exam/                     # Exam system services
│       ├── MockTestService.java
│       ├── QuestionService.java
│       └── TestAttemptService.java
├── repository                    # JPA repositories
│   ├── NotificationRepository.java
│   └── exam/                     # Exam system repositories
│       ├── MockTestRepository.java
│       ├── SectionRepository.java
│       ├── QuestionRepository.java
│       ├── OptionRepository.java
│       ├── TestAttemptRepository.java
│       ├── UserAnswerRepository.java
│       └── ResultRepository.java
├── entity                        # JPA entities
│   ├── Notification.java
│   └── exam/                     # Exam system entities
│       ├── MockTest.java
│       ├── Section.java
│       ├── Question.java
│       ├── Option.java
│       ├── TestAttempt.java
│       ├── UserAnswer.java
│       └── Result.java
├── dto                           # Request/Response DTOs
│   ├── NotificationDto.java
│   └── exam/                     # Exam system DTOs
│       ├── MockTestDto.java
│       ├── SectionDto.java
│       ├── QuestionDto.java
│       ├── OptionDto.java
│       ├── TestAttemptDto.java
│       ├── UserAnswerDto.java
│       └── ResultDto.java
├── exception                     # Custom & global exceptions
├── config                        # Swagger, Mapper configs
└── DefencePortalApplication.java
```
## 🔗 REST API Modules

### 📢 Notification APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/notifications` | Create notification |
| PUT | `/api/notifications/{id}` | Update notification |
| GET | `/api/notifications` | Get all notifications |
| GET | `/api/notifications/{id}` | Get notification by ID |
| DELETE | `/api/notifications/{id}` | Delete notification |

### 📝 Mock Test APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/exam/mock-tests` | Create mock test |
| PUT | `/api/exam/mock-tests/{id}` | Update mock test |
| GET | `/api/exam/mock-tests` | Get all mock tests |
| GET | `/api/exam/mock-tests/{id}` | Get mock test by ID |
| GET | `/api/exam/mock-tests/published` | Get published mock tests |
| GET | `/api/exam/mock-tests/exam-type/{examType}` | Get mock tests by exam type |
| PUT | `/api/exam/mock-tests/{id}/publish` | Publish mock test |
| DELETE | `/api/exam/mock-tests/{id}` | Delete mock test |

### ❓ Question Management APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/exam/questions` | Create question |
| PUT | `/api/exam/questions/{id}` | Update question |
| GET | `/api/exam/questions` | Get all questions |
| GET | `/api/exam/questions/{id}` | Get question by ID |
| GET | `/api/exam/questions/section/{sectionId}` | Get questions by section |
| DELETE | `/api/exam/questions/{id}` | Delete question |

### 📋 Test Attempt APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/exam/attempts/start` | Start a test attempt |
| PUT | `/api/exam/attempts/{attemptId}/submit` | Submit test attempt |
| GET | `/api/exam/attempts` | Get all test attempts |
| GET | `/api/exam/attempts/{id}` | Get test attempt by ID |
| GET | `/api/exam/attempts/user/{userId}` | Get attempts by user |
| GET | `/api/exam/attempts/mock-test/{mockTestId}` | Get attempts by mock test |
| DELETE | `/api/exam/attempts/{id}` | Delete test attempt |

### 📊 Result APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/notifications?type=RESULT_ANNOUNCED` | Result notifications |
| GET | `/api/notifications?status=ACTIVE` | Active exams |

**Note:** Additional controllers for Section, Option, UserAnswer, and Result management are planned for future implementation. Currently, these entities are managed through the existing MockTest, Question, and TestAttempt APIs.

## 📋 API Request/Response Examples

### Create Mock Test
```json
POST /api/exam/mock-tests
{
  "title": "BSF Constable Mock Test 2024",
  "examType": "BSF",
  "description": "Complete mock test for BSF Constable exam",
  "totalMarks": 100,
  "durationMinutes": 60,
  "negativeMarking": true,
  "status": "DRAFT"
}
```

### Create Question
```json
POST /api/exam/questions
{
  "questionText": "What is the capital of India?",
  "questionType": "MCQ",
  "marks": 4,
  "negativeMarks": 1,
  "difficultyLevel": "EASY",
  "sectionId": 1
}
```

### Start Test Attempt
```json
POST /api/exam/attempts/start
{
  "userId": 123,
  "mockTestId": 1
}
```

### Submit Answer
```json
POST /api/exam/user-answers
{
  "attemptId": 1,
  "questionId": 1,
  "selectedOptionId": 2
}
```
## 📘 Swagger API Documentation

Swagger UI is enabled for API testing and documentation.
```
http://localhost:8080/swagger-ui/index.html
```
## � Current Implementation Status

### ✅ **Fully Implemented APIs**
- **MockTest APIs** (8 endpoints) - Complete CRUD + publish + filtering
- **Question APIs** (6 endpoints) - Complete CRUD + section filtering
- **TestAttempt APIs** (7 endpoints) - Start, submit, track attempts
- **Notification APIs** (5 endpoints) - Complete CRUD operations

### 🔄 **Partially Implemented**
- **Database Layer** - All entities and repositories created
- **DTO Layer** - All DTOs defined for future API implementation
- **Basic Result Tracking** - Through test attempts

### 📋 **Planned for Future Implementation**
- **Section Management APIs** - CRUD operations for test sections
- **Option Management APIs** - CRUD operations for question options
- **UserAnswer APIs** - Individual answer submission and tracking
- **Advanced Analytics** - Detailed performance metrics and reporting

### 🧪 **Testing Ready**
- Postman collection with all implemented APIs
- Sample data script for quick testing
- Comprehensive API documentation
- Swagger UI integration

MySQL relational database

Normalized schema

Dynamic lists handled using @ElementCollection

Large text fields handled using @Lob

Audit fields (createdAt, updatedAt)

### 📊 Exam System Entity Relationships

```
MockTest (1) ──── (N) Section (1) ──── (N) Question (1) ──── (N) Option
    │                     │                     │
    │                     │                     │
    │                     │                     │
    ↓                     ↓                     ↓
TestAttempt (1) ──── (N) UserAnswer           Result (1)
    │
    ↓
  User (External)
```

**Key Relationships:**
- MockTest contains multiple Sections
- Section contains multiple Questions
- Question has multiple Options (with one correct answer)
- TestAttempt links User to MockTest
- UserAnswer records each question's response
- Result summarizes the TestAttempt performance

## 🚀 Quick Start Testing

1. **Start the Application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Import Postman Collection:**
   - Import `Defence_Portal_Postman_Collection.json`
   - Set environment variables

3. **Run Tests in Order:**
   - Create Mock Test → Get ID
   - Create Question (use sectionId: 1 for now)
   - Publish Mock Test
   - Start Test Attempt → Get ID
   - Submit Test Attempt
   - Check Results via User Attempts API

4. **Verify in Swagger:**
   - Visit: `http://localhost:8080/swagger-ui/index.html`
   - Test APIs interactively

### Prerequisites
- Java 21
- MySQL 8.0
- Maven 3.6+
- Postman (for API testing)

### Database Setup
1. Create MySQL database:
   ```sql
   CREATE DATABASE defence_portal_db;
   ```

2. Update `.env` file with your database credentials:
   ```
   DB_URL=jdbc:mysql://localhost:3306/defence_portal_db
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   DB_DRIVER=com.mysql.cj.jdbc.Driver
   SERVER_PORT=8080
   ```

### Run Application
```bash
# Using Maven wrapper (Windows)
.\mvnw spring-boot:run

# Using Maven (Linux/Mac)
./mvnw spring-boot:run

# Or using Maven directly
mvn spring-boot:run
```

### Sample Data (Optional)
For quick testing, run the sample data script:
```bash
mysql -u your_username -p defence_portal_db < sample_data.sql
```

**File:** `sample_data.sql` - Contains pre-populated mock test, questions, and sample results.

## 🧪 API Testing with Postman

### 📋 Setup Instructions

1. **Install Postman** (if not already installed)
2. **Import Collection** or create new collection named "Defence Portal APIs"
3. **Set Base URL**: `http://localhost:8080`
4. **Create Environment** with variables:
   - `base_url`: `http://localhost:8080`
   - `mock_test_id`: (will be set after creating mock test)
   - `section_id`: (will be set after creating section)
   - `question_id`: (will be set after creating question)
   - `attempt_id`: (will be set after starting attempt)

### 🔄 Complete Testing Flow

#### 1. **Create Mock Test**
```
Method: POST
URL: {{base_url}}/api/exam/mock-tests
Headers:
  Content-Type: application/json

Body (raw JSON):
{
  "title": "BSF Constable Mock Test 2024",
  "examType": "BSF",
  "description": "Complete mock test for BSF Constable exam",
  "totalMarks": 100,
  "durationMinutes": 60,
  "negativeMarking": true,
  "status": "DRAFT"
}
```
**Expected Response:** 201 Created with mock test details. Copy the `id` and set as `mock_test_id` environment variable.

#### 2. **Create Question** (Note: Sections are created automatically through MockTest relationships)
```
Method: POST
URL: {{base_url}}/api/exam/questions
Headers:
  Content-Type: application/json

Body (raw JSON):
{
  "questionText": "What is the capital of India?",
  "questionType": "MCQ",
  "marks": 4,
  "negativeMarks": 1,
  "difficultyLevel": "EASY",
  "sectionId": 1
}
```
**Expected Response:** 201 Created with question details. Copy the `id` and set as `question_id`.

#### 3. **Publish Mock Test**
```
Method: PUT
URL: {{base_url}}/api/exam/mock-tests/{{mock_test_id}}/publish
Headers:
  Content-Type: application/json

Body: (empty)
```
**Expected Response:** 200 OK with published mock test.

#### 4. **Start Test Attempt**
```
Method: POST
URL: {{base_url}}/api/exam/attempts/start
Headers:
  Content-Type: application/json

Body (raw JSON):
{
  "userId": 123,
  "mockTestId": {{mock_test_id}}
}
```
**Expected Response:** 201 Created with attempt details. Copy the `id` and set as `attempt_id`.

#### 5. **Submit Test Attempt** (Note: Individual answer submission not yet implemented)
```
Method: PUT
URL: {{base_url}}/api/exam/attempts/{{attempt_id}}/submit
Headers:
  Content-Type: application/json

Body: (empty)
```
**Expected Response:** 200 OK with submitted attempt.

#### 6. **Get Results**
```
Method: GET
URL: {{base_url}}/api/exam/attempts/user/123
Headers: (none needed)

Expected Response: Array of attempts with scores
```

**Note:** Currently, options and user answers are managed internally. Future updates will include dedicated APIs for these entities.

### 📊 Individual API Tests

#### Mock Test APIs
- **GET All Mock Tests:** `GET {{base_url}}/api/exam/mock-tests`
- **GET Published Tests:** `GET {{base_url}}/api/exam/mock-tests/published`
- **GET By Exam Type:** `GET {{base_url}}/api/exam/mock-tests/exam-type/BSF`
- **GET Single Test:** `GET {{base_url}}/api/exam/mock-tests/{{mock_test_id}}`
- **Update Test:** `PUT {{base_url}}/api/exam/mock-tests/{{mock_test_id}}` (use same JSON as create)
- **Delete Test:** `DELETE {{base_url}}/api/exam/mock-tests/{{mock_test_id}}`

#### Question APIs
- **GET All Questions:** `GET {{base_url}}/api/exam/questions`
- **GET By Section:** `GET {{base_url}}/api/exam/questions/section/{{section_id}}`
- **GET Single Question:** `GET {{base_url}}/api/exam/questions/{{question_id}}`
- **Update Question:** `PUT {{base_url}}/api/exam/questions/{{question_id}}`
- **Delete Question:** `DELETE {{base_url}}/api/exam/questions/{{question_id}}`

#### Test Attempt APIs
- **GET All Attempts:** `GET {{base_url}}/api/exam/attempts`
- **GET By User:** `GET {{base_url}}/api/exam/attempts/user/123`
- **GET By Mock Test:** `GET {{base_url}}/api/exam/attempts/mock-test/{{mock_test_id}}`
- **GET Single Attempt:** `GET {{base_url}}/api/exam/attempts/{{attempt_id}}`
- **Delete Attempt:** `DELETE {{base_url}}/api/exam/attempts/{{attempt_id}}`

### 🔧 Postman Tips

1. **Use Tests Tab** to automatically set environment variables:
   ```javascript
   if (pm.response.code === 201) {
       const response = pm.response.json();
       pm.environment.set("mock_test_id", response.id);
   }
   ```

2. **Enable Auto-Save** to preserve your requests

3. **Use Collections** to group related requests

4. **Add Tests** for validation:
   ```javascript
   pm.test("Status code is 201", function () {
       pm.response.to.have.status(201);
   });
   ```

5. **Use Pre-request Scripts** for dynamic data:
   ```javascript
   pm.globals.set("timestamp", new Date().toISOString());
   ```

### 🚨 Common Issues & Solutions

- **404 Not Found:** Check if application is running on port 8080
- **400 Bad Request:** Validate JSON structure and required fields
- **500 Internal Server Error:** Check application logs for database connection issues
- **Foreign Key Errors:** Ensure parent entities exist before creating child entities

### 📱 Testing Checklist

- [ ] Create Mock Test
- [ ] Create Question
- [ ] Publish Mock Test
- [ ] Start Test Attempt
- [ ] Submit Test Attempt
- [ ] Get Test Results
- [ ] Test CRUD operations for Mock Tests
- [ ] Test CRUD operations for Questions
- [ ] Test CRUD operations for Test Attempts

**Future Testing Items (APIs not yet implemented):**
- [ ] Create/Manage Sections
- [ ] Create/Manage Options
- [ ] Submit Individual Answers
- [ ] Advanced Result Analytics

### 📦 Postman Collection

A ready-to-import Postman collection is available in the repository:

**File:** `Defence_Portal_Postman_Collection.json`

**Import Steps:**
1. Open Postman
2. Click "Import" button
3. Select "File" tab
4. Choose `Defence_Portal_Postman_Collection.json`
5. Import the collection and environment

**Features:**
- Pre-configured requests for implemented APIs (MockTest, Question, TestAttempt)
- Environment variables for dynamic IDs
- Automatic variable setting from responses
- Organized folder structure

**API Coverage:**
- **Mock Test APIs** (8 endpoints): Create, read, update, delete, publish, filter by exam type
- **Question APIs** (6 endpoints): Create, read, update, delete, filter by section
- **Test Attempt APIs** (7 endpoints): Start, submit, track attempts, filter by user/mock test

**Note:** The collection currently includes APIs that are fully implemented. Additional APIs for Section, Option, and UserAnswer management will be added in future updates.

## 🚀 Future Enhancements

### 🔐 Security & Authentication
- Spring Security (JWT, Admin/User roles)
- Role-based access control for APIs
- User authentication and authorization

### 📊 Advanced Features
- Section management APIs (create, update, delete sections)
- Option management APIs (create, update options for questions)
- User answer submission APIs (individual answer tracking)
- Result analytics and performance metrics
- Mock test evaluation & advanced score calculation

### 🔧 Technical Improvements
- Pagination & filtering for all list APIs
- Caching implementation (Redis)
- API rate limiting
- Request/response logging
- API versioning

### 🚀 Deployment & DevOps
- Docker containerization
- Kubernetes deployment
- CI/CD pipeline setup
- Database migration scripts
- Monitoring and logging (ELK stack)

### 📱 Frontend Integration
- CORS configuration for web clients
- API documentation improvements
- File upload support (for question images)
- Real-time notifications (WebSocket)

## 🎤 Interview Summary (IMPORTANT)

"This is a comprehensive backend Spring Boot application that provides REST APIs for defence exam notifications and a mock test system. It follows clean layered architecture with DTOs, service interfaces, transactional business logic, JPA repositories, and global exception handling. Currently implemented features include: complete mock test management (create, publish, filter by exam type), question bank management, and test attempt lifecycle (start, submit, track results). The system uses MySQL database with proper entity relationships and includes comprehensive API documentation with Swagger. Future enhancements will include dedicated APIs for sections, options, and individual answer submissions."

## 👨‍💻 Author

Mahesh Yadav
Java Backend Developer
📧 mahi234xp@gmail.com

🔗 GitHub: https://github.com/mahesh-ryadav
