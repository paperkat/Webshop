# Webshop Backend (Spring Boot PoC)

## Overview

This is a backend proof-of-concept for a simple webshop application built with **Spring Boot 4.0** and **Java 17**.

The project currently supports basic **user, product, and cart management** functionality.

### User Management
- Create a new user
- Get all users
- Get user by ID
- Get user by username
- Delete a user

### Product Management
- Create a new product
- Get all products
- Get product by ID
- Get products by name
- Delete a product

### Cart Management
- Create a cart
- Get all carts
- Get cart by ID
- Delete a cart

Uses an H2 in-memory database by default. Data is lost when the application restarts.

## Project Structure

```text
Webshop/
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
- ✅ User service and controller tests
- ✅ Unique username/email enforcement with friendly API errors
- ✅ Product entity, repository, service, and controller implemented
- ✅ Product service and controller tests
- ✅ Spring Security configuration with HTTP Basic authentication and role-based access
- ✅ Cart entity, repository, service, and controller implemented
- ✅ Cart controller integration tests for creation, retrieval, and deletion
- ❌ CartItem functionality not yet implemented
- ❌ Order and OrderItem functionality not yet implemented

## TODO

- [ ] Implement `CartItem` functionality
- [ ] Implement `Order` and `OrderItem` functionality
- [ ] Add validation and input sanitization
- [ ] Add global exception handling (`@ControllerAdvice`)
- [ ] Expand test coverage
- [ ] Create a frontend (React) to consume these APIs

## How to Run

1. Clone the repository.
2. Open the project in your IDE.
3. Run the Spring Boot application.

The application is available at `http://localhost:8080` by default.

## API Endpoints

### User API

Base URL: `/api/users`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users` | Create a user |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get a user by ID |
| `GET` | `/api/users/username/{username}` | Get users by username |
| `DELETE` | `/api/users/{id}` | Delete a user |

### Product API

Base URL: `/api/products`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/products` | Create a product |
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get a product by ID |
| `GET` | `/api/products/name/{name}` | Get products by name |
| `DELETE` | `/api/products/{id}` | Delete a product |

### Cart API

Base URL: `/api/carts`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/carts` | Create a cart |
| `GET` | `/api/carts` | Get all carts |
| `GET` | `/api/carts/{id}` | Get a cart by ID |
| `DELETE` | `/api/carts/{id}` | Delete a cart |

A successful cart deletion returns `204 No Content`. A subsequent GET request for the deleted cart returns `404 Not Found`.

## Security

Security is disabled by default for development. To enable the secure configuration, run the application with the `security` profile enabled:

```text
--spring.profiles.active=security
```

This can be configured in IntelliJ under **Run → Edit Configurations**.

### Authentication & Authorization

The following rules apply when the `security` profile is active:

- HTTP Basic authentication
- In-memory users (PoC only)
- Role-based access control:
    - `GET /api/**` → public
    - `POST /api/**` → ADMIN only
    - `DELETE /api/**` → ADMIN only

### Preconfigured Users

| Username | Password | Role |
|---|---|---|
| `user` | `password` | USER |
| `admin` | `password` | ADMIN |

These credentials are for proof-of-concept development only.

## Sample User JSON

```json
{
  "username": "tony.stark",
  "email": "tony@starkindustries.com",
  "password": "ironman123"
}
```