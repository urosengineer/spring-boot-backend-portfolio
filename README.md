# spring-boot-backend-portfolio

This codebase is curated and maintained by Urke, a backend engineer specializing in enterprise Java/Spring Boot solutions. It demonstrates best practices in modern backend architecture, API design, and production-quality engineering.

## Features

- REST API with Swagger/OpenAPI documentation
- Real-time WebSocket messaging (user presence, chat events)
- Custom JWT authentication/authorization
- Modular, domain-driven design
- Advanced file & media storage management
- Integration: MOS TCP server (Netty), automation protocols
- Audit logging, user management, multi-tenancy

## Technologies

- Java 17+
- Spring Boot, Spring Security, Spring Data JPA
- Netty (TCP server)
- STOMP/WebSocket
- MariaDB/MySQL
- Gradle/Maven

## How to Run

1. Configure database in `application.properties`
2. Run: `./gradlew bootRun`
3. API docs: [[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html](https://localhost:8443/api/swagger-ui/index.html#/))

## Example API Usage

### User Authentication (Login)
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

```

```json
{
  "accessToken": "...",
  "refreshToken": "...",
  "tokenType": "Bearer",
  "username": "admin",
  "fullName": "Admin",
  "role": "ADMIN",
  "user_id": 6
}


### Create New User

```http
POST /api/users
Authorization: Bearer <token>
Content-Type: application/json

{
  "username": "johndoe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "SuperSecret123",
  "roleId": 2,
  "clientId": 1
}

```

## Portfolio & Contact

For more information, proposals, or consulting inquiries, please contact via Upwork.

