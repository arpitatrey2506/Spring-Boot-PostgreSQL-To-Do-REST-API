# Spring Boot PostgreSQL To-Do REST API

A lightweight, robust REST API for managing user-specific To-Do tasks, built with Java, Spring Boot, Spring Data JPA, and PostgreSQL.

---

## 🛠️ Technology Stack
* **Language:** Java 17
* **Framework:** Spring Boot
* **ORM / Database Access:** Spring Data JPA (Hibernate)
* **Database:** PostgreSQL (Run via Docker)
* **Build Tool:** Maven

---

## ⚙️ Prerequisites
Before running the application, make sure you have the following installed:
* [Java Development Kit (JDK) 17](https://adoptium.net/) or higher
* [Docker](https://www.docker.com/products/docker-desktop/)
* [Maven](https://maven.org/) (optional, as the Maven Wrapper `./mvnw` is included in the project)

---

## 🚀 Getting Started

### 1. Start the PostgreSQL Container
Launch the PostgreSQL database container in Docker by running this command in your terminal:
```bash
docker run --name todo-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=tododb -p 5432:5432 -d postgres
```

### 2. Configure Settings (Optional)
Database credentials are pre-configured in `src/main/resources/application.properties`. You can modify them if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 3. Build & Run the Application
Navigate to the root directory of the project and execute:

* **Build the project:**
  ```bash
  ./mvnw clean package
  ```
* **Run the application:**
  ```bash
  ./mvnw spring-boot:run
  ```

Once started, the API will be available at `http://localhost:8080/users/{userId}/tasks`.

---

## 🔌 API Endpoints
All request and response bodies use the application/json format.

| HTTP Method | Endpoint | Description | Request Body | Response Body |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/users/{userId}/tasks` | Get all tasks for a specific user | *None* | Array of Task objects |
| **GET** | `/users/{userId}/tasks/{id}` | Get a single task by ID and user | *None* | Task object (or `null` if not found) |
| **POST** | `/users/{userId}/tasks` | Create a new task for a user | Task JSON (omit `id` and `userId`) | Created Task object with DB-assigned ID |
| **PUT** | `/users/{userId}/tasks/{id}` | Update an existing task for a user | Task JSON (updated properties) | Updated Task object (or `null`) |
| **DELETE** | `/users/{userId}/tasks/{id}` | Delete a task for a user | *None* | Status string (`"Task Deleted"`) |
| **GET** | `/tasks/search` | Search tasks for a user by their ID | *None* | Array of Task objects |

### Example Payloads

#### **Add Task for Existing User (POST `/users/1/tasks`)**
**Request Body:**
```json
{
  "title": "Task for existing user",
  "completed": false
}
```    
**Response Body (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "title": "Task for existing user",
  "completed": false
}
```

#### **Add Task for New User / First Time (POST `/users/2/tasks`)**
**Request Body:**
```json
{
  "title": "My first task",
  "completed": false,
  "userDetails": {
    "name": "Jane Smith",
    "address": "San Francisco",
    "email": "jane.smith@gmail.com"
  }
}
```
**Response Body (200 OK):**
```json
{
  "id": 2,
  "userId": 2,
  "title": "My first task",
  "completed": false
}
```

#### **Update Task (PUT `/users/1/tasks/1`)**
**Request Body:**
```json
{
  "title": "Updated task title",
  "completed": true
}
```
**Response Body (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "title": "Updated task title",
  "completed": true
}
```

---

## 🏗️ Project Structure & Architecture
The project follows a standard **Layered Architecture**:

```
src/main/java/com/example/todo/
 ├── TodoApplication.java            # Entry point of the Spring Boot application
 ├── controller/
 │    └── TaskController.java        # Exposes the REST endpoints
 ├── model/
 │    └── Task.java                  # JPA Database Entity representing the task table
 ├── repository/
 │    └── TaskRepository.java        # Spring Data JPA Repository interface
 └── services/
      └── TaskService.java           # Service layer containing the business logic
```

* **TaskController.java:** Receives HTTP requests, maps request paths, handles payloads, and delegates processing to the service layer.
* **TaskService.java:** Contains business logic rules and bridges the Controller to the Repository.
* **TaskRepository.java:** Extends `JpaRepository` to perform JDBC commands (SELECT, INSERT, UPDATE, DELETE) against the PostgreSQL instance.
* **Task.java:** Map annotations map Java classes directly to PostgreSQL relational tables.
