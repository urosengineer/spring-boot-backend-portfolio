# spring-boot-backend-portfolio

This codebase is curated and maintained by Uros, a backend engineer specializing in enterprise Java/Spring Boot solutions. It demonstrates best practices in modern backend architecture, API design, and production-quality engineering.

This repository demonstrates an extensible, production-grade backend platform tailored for newsrooms, broadcast, and automation use cases. It showcases how to architect, document, and secure a modern Java backend following best enterprise practices.

---

## Features

- REST API with Swagger/OpenAPI documentation
- Real-time WebSocket messaging (user presence, chat events)
- Custom JWT authentication/authorization
- Modular, domain-driven design
- Advanced file & media storage management
- Integration: MOS TCP server (Netty), automation protocols
- Audit logging, user management, multi-tenancy

## Technologies

- Java 21+
- Spring Boot, Spring Security, Spring Data JPA
- Netty (TCP server)
- STOMP/WebSocket
- MariaDB/MySQL
- Maven (build tool)  
  > *Docker support is available in the full, private repository.*
  > 
  > *The full codebase can be adapted for Gradle builds upon request.*

---

## ⚠️ Portfolio Notice

**This repository contains only a subset of the codebase, published for portfolio and code review purposes.**  
It is not a complete, runnable application and is not intended for deployment.

- Only key modules, representative controllers, entities, DTOs, and configuration are included.
- Full business logic, all features, and proprietary integrations remain private.
- **Build and run instructions are intentionally omitted** — the project is not meant to be built or executed in this form.

---

## API Documentation

All endpoints are fully documented using Swagger/OpenAPI (see project screenshots).


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
```

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

## Project Structure

- `NmsServerApplication` — Spring Boot entrypoint, app lifecycle hooks
- `config/` — Security, WebSocket, storage, Swagger, CORS, persistence and all application configuration
- `controller/` — REST and WebSocket controllers (users, stories, rundown, chat, media, MOS integration, etc.)
- `domain/` — JPA entities and enums (50+ normalized tables covering all newsroom features, including audit and activity logs)
- `dto/` — DTOs for requests, responses, filtering, search
- `repository/` — Spring Data repositories for all main entities
- `service/` — Core business logic and feature orchestration (including helpers)
- `tcp/` — Netty TCP server and MOS protocol handlers
- `websocket/` — WebSocket events, session listeners, presence tracking
- `exception/` — Global and domain exception handling
- `util/` — Utility/helper classes (PDF, Word, metadata, etc.)

### API Documentation (Swagger UI Example)

This project provides fully documented REST endpoints using Swagger/OpenAPI.
Below are example screenshots from the live API documentation:

**User Management endpoints overview:**

![Swagger User Management](docs/swagger_user_managment.png)

**Create User endpoint detail:**

![Swagger Create User](docs/swagger_create_user.png)



## Portfolio & Contact

For proposals or consulting inquiries, contact me via [Upwork](https://www.upwork.com/freelancers/~01fec0932cf5314b14).

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

## Contribution
This is a personal portfolio project; external contributions are not being accepted at this time.
