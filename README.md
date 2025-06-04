spring-boot-backend-portfolio

A portfolio repository featuring selected Java Spring Boot backend modules, demonstrating modern enterprise software architecture and production-ready engineering patterns.

Key highlights included in this codebase:

REST API design with Swagger/OpenAPI documentation

Real-time WebSocket messaging (with user presence and chat events)

Custom authentication and authorization using JWT

Modular service and domain-driven architecture

Advanced file storage and media management utilities

Real-time event processing with application lifecycle hooks

Integration with external systems (MOS TCP server, automation protocols)

Application Entrypoint

NmsServerApplication serves as the entry point for the Spring Boot application.It demonstrates advanced use of lifecycle hooks (@PostConstruct, @EventListener), structured configuration integration, and external service health checks on startup.

All modules reflect industry-standard practices for building scalable, secure, and maintainable backend systems.

This repository is intended for demonstration, portfolio, and freelance project proposals.

API Usage Examples

Below are a few example API requests and responses for key endpoints.Note: Full API documentation is available via Swagger/OpenAPI once the application is running locally.


1. User Authentication (Login)

Request
  POST /api/auth/login
  Content-Type: application/json
  
  {
    "username": "admin",
    "password": "admin123"
  }

Response
  {
    "accessToken": "eyJhbGc...",
    "refreshToken": "UUID...",
    "tokenType": "Bearer",
    "username": "admin",
    "fullName": "Admin",
    "role": "ADMIN",
    "user_id": 6
  }


2. Create New User

Request
  POST /api/users
  Authorization: Bearer <accessToken>
  Content-Type: application/json
  
  {
    "username": "johndoe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "password": "SuperSecret123",
    "roleId": 2,
    "clientId": 1
  }

Response
  {
    "id": 7,
    "username": "johndoe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "role": "USER",
    "status": "ACTIVE",
    "createdAt": "2025-06-04T09:00:00"
  }


3. Retrieve Current Authenticated User

Request
  GET /api/users/me
  Authorization: Bearer <accessToken>
  
  Response
  {
    "id": 6,
    "username": "admin",
    "fullName": "Test Admin",
    "email": "admin@example.com",
    "role": "ADMIN",
    "status": "ACTIVE"
  }
