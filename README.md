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

## 👨‍💻 Author

Mahesh Yadav
Java Backend Developer
📧 mahi234xp@gmail.com

🔗 GitHub: https://github.com/mahesh-ryadav
