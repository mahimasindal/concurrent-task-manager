# Concurrent Task Manager

A Java-based backend application designed to manage and execute tasks asynchronously using configurable worker threads. The system focuses on concurrent processing, reliable task execution, database consistency, and fault handling.

The goal is to build a robust task processing system that can accept multiple tasks, execute them concurrently, track their execution lifecycle, and handle failures gracefully.

## Key Objectives

- Build RESTful APIs for task creation, retrieval, and management.
- Implement asynchronous task execution using configurable thread pools.
- Support concurrent processing while maintaining data consistency.
- Track task states and execution history.
- Implement retry mechanisms and safeguards against duplicate execution.
- Handle failures, transaction boundaries, and concurrent database updates reliably.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Core application development |
| Spring Boot | Application framework and REST APIs |
| Spring Data JPA | Database access and repository abstraction |
| Hibernate | ORM and persistence management |
| Maven | Dependency management and build automation |
| H2 Database | Development database |
| Jakarta Bean Validation | Request validation |
| Git | Version control |

## Architecture

The application follows a layered architecture to separate API handling, business logic, and data persistence.

```text
Client
  |
  v
REST Controller
  |
  v
Service Layer
  |
  +----------------------+
  |                      |
  v                      v
Task Repository     Task Execution Engine
  |                      |
  v                      v
Database             Worker Thread Pool
                         |
                         v
                   Task Processing
```

## Features

### Implemented

- REST APIs to create and retrieve tasks.
- Task lifecycle states: `PENDING`, `PROCESSING`, `COMPLETED`, and `FAILED`.
- Persistence using Spring Data JPA and Hibernate.
- Repository queries using Spring Data derived query methods.
- DTO-based request and response handling.
- Request validation.
- Transaction management using Spring's `@Transactional`.
- Centralized exception handling using `@RestControllerAdvice`.
- Custom exception handling for missing tasks.

### Under Development

- Asynchronous task execution using configurable worker thread pools.
- Concurrent task processing and execution state management.
- Execution history and task lifecycle tracking.
- Retry handling for failed tasks.
- Duplicate execution prevention and idempotency.
- Optimistic locking for concurrent database updates.
- Graceful worker shutdown and failure recovery.
- Unit and integration testing.

## API Endpoints

Base URL: `/api/tasks`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | Retrieve all tasks |
| GET | `/api/tasks/{id}` | Retrieve a task by ID |
| GET | `/api/tasks?status=PENDING` | Filter tasks by status |

### Example: Create a Task

**Request**

```http
POST /api/tasks
Content-Type: application/json
```

```json
{
  "title": "Process customer data",
  "description": "Validate and process customer records"
}
```

**Response**

```json
{
  "id": 1,
  "title": "Process customer data",
  "description": "Validate and process customer records",
  "status": "PENDING",
  "createdAt": "2026-11-10T10:30:00",
  "updatedAt": "2026-11-10T10:30:00"
}
```

## Running Locally

### Prerequisites

- Java 21 or later
- Maven 3.9+
- Git

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/concurrent-task-manager.git
cd concurrent-task-manager
```

### Run the application

```bash
./mvnw spring-boot:run
```

The application runs at:

```text
http://localhost:8081
```

### Database Console

The application currently uses an in-memory H2 database.

H2 console:

```text
http://localhost:8081/h2-console
```

Connection details:

- JDBC URL: `jdbc:h2:mem:taskmanager`
- Username: `sa`
- Password: Leave blank

## Engineering Focus

Beyond basic CRUD functionality, this project explores important backend engineering challenges:

- Thread pool sizing and worker lifecycle management.
- Race conditions and thread safety.
- Transaction boundaries and database consistency.
- Concurrent state transitions.
- Idempotency and retry semantics.
- ORM performance and query optimization.
- Failure handling and application reliability.

## Project Status

Actively under development. The initial REST API and persistence layers are implemented. Asynchronous execution, concurrency management, and execution reliability are being developed next.