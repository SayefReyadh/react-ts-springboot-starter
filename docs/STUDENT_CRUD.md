# Student CRUD Application

This is a full-stack Student Management System with CRUD (Create, Read, Update, Delete) operations.

## Features

### Backend (Spring Boot)
- **Student Entity**: Auto-generated ID, Name, Email, University ID
- **REST API Endpoints**:
  - `GET /api/students` - Get all students
  - `GET /api/students/{id}` - Get student by ID
  - `POST /api/students` - Create new student
  - `PUT /api/students/{id}` - Update student
  - `DELETE /api/students/{id}` - Delete student
- **H2 In-Memory Database** for data persistence
- **JPA/Hibernate** for ORM

### Frontend (React + TypeScript)
- **Student Form**: Create and edit students
- **Student List**: Display all students in a table
- **Student Details**: View individual student information in a modal
- **Action Buttons**:
  - View - Show student details
  - Edit - Update student information
  - Delete - Remove student (with confirmation)

## How to Run

### Backend
```bash
cd backend
./mvnw spring-boot:run
```
Backend will run on http://localhost:8080

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend will run on http://localhost:3000

## Database Console

Access H2 Database Console at: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:studentdb`
- Username: `sa`
- Password: (leave empty)

## API Testing

### Create Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@university.edu",
    "universityId": "STU001"
  }'
```

### Get All Students
```bash
curl http://localhost:8080/api/students
```

### Get Student by ID
```bash
curl http://localhost:8080/api/students/1
```

### Update Student
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@university.edu",
    "universityId": "STU001"
  }'
```

### Delete Student
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

## Project Structure

### Backend
```
backend/src/main/java/com/example/demo/
├── model/
│   └── Student.java              # Entity class
├── repository/
│   └── StudentRepository.java    # JPA Repository
├── service/
│   └── StudentService.java       # Business logic
└── controller/
    └── StudentController.java    # REST endpoints
```

### Frontend
```
frontend/src/
├── types/
│   └── Student.ts                # TypeScript interface
├── services/
│   └── studentApi.ts             # API service
├── components/
│   ├── StudentForm.tsx           # Create/Edit form
│   ├── StudentList.tsx           # Student table
│   └── StudentDetails.tsx        # Details modal
└── App.tsx                       # Main application
```

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.1.4
- Spring Data JPA
- H2 Database
- Maven

### Frontend
- React 18
- TypeScript
- Vite
- Fetch API
