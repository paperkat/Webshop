# Webshop Backend (Spring Boot PoC)

## Overview
This is a backend proof-of-concept for a simple webshop application built with **Spring Boot 4.0** and **Java 17**.
The project currently supports basic **user management** functionality, including:

- Create a new user 
- Get all users
- Get user by ID
- Get user by username
- Delete a user

- Uses H2 in-memory database by default; data is lost on app restart.
## Project Structure

```text
Webshop/
├── .idea/
├── .mvn/
├── Docs/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/demo/webshop/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   └── test/
├── README.md
└── pom.xml

```
## Current Status
- ✅ User entity, repository, service, and controller implemented
- ✅ Unique username/email enforcement with friendly API error
- ✅ Basic CRUD endpoints for users
- ❌ Security is disabled for PoC/testing purposes
- ❌ Other entities (Cart, CartItem, Product, Order, OrderItem) not yet implemented

## TODO
- [ ] Implement `Product` entity and related repository/service/controller
- [ ] Implement `Cart` and `CartItem` functionality
- [ ] Implement `Order` and `OrderItem` functionality
- [ ] Add validation and input sanitization
- [ ] Add global exception handling (`@ControllerAdvice`)
- [ ] Add JWT or session-based authentication
- [ ] Create a frontend (React) to consume these APIs
- [ ] Add unit and integration tests

## How to Run
1. Clone the repository
2. Open in your IDE
3. Run the Spring Boot application
4. Access API endpoints via `http://localhost:8080/api/users`  
   Example: `GET /api/users` or `POST /api/users` with JSON body

## Sample User JSON
```json

{
  "username": "tony.stark",
  "email": "tony@starkindustries.com",
  "password": "ironman123"
}
```

