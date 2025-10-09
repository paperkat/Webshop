# 🧱 Entity Relationship Design (ERD)

## Overview
This document describes the core data model for the **Webshop** backend application.  
It defines all entities, their key fields, and the relationships between them.

---

## 🧍 User
**Table:** `users`

| Field | Type | Description |
|--------|------|-------------|
| `id` | Long (PK) | Unique user ID |
| `username` | String | Username |
| `email` | String | User email |
| `password` | String | Hashed password |
| `role` | String | User role (e.g., `ADMIN`, `CUSTOMER`) |

**Relationships:**
- One-to-One → `Cart`
- One-to-Many → `Order`

---

## 🛒 Cart
**Table:** `carts`

| Field | Type | Description |
|--------|------|-------------|
| `id` | Long (PK) | Unique cart ID |
| `user_id` | Long (FK → users.id) | Owner of the cart |
| `total_price` | Double | Total cart value |

**Relationships:**
- One-to-One → `User`
- One-to-Many → `CartItem`

---

## 🧾 CartItem
**Table:** `cart_items`

| Field | Type | Description |
|--------|------|-------------|
| `id` | Long (PK) | Unique cart item ID |
| `cart_id` | Long (FK → carts.id) | Cart this item belongs to |
| `product_id` | Long (FK → products.id) | Product added to cart |
| `quantity` | Integer | Quantity of product |
| `subtotal` | Double | Price × Quantity |

**Relationships:**
- Many-to-One → `Cart`
- Many-to-One → `Product`

---

## 📦 Product
**Table:** `products`

| Field | Type | Description |
|--------|------|-------------|
| `id` | Long (PK) | Unique product ID |
| `name` | String | Product name |
| `description` | String | Product details |
| `price` | Double | Price per unit |
| `stock_quantity` | Integer | Available stock |
| `category` | String | Optional category name |

**Relationships:**
- One-to-Many → `CartItem`
- One-to-Many → `OrderItem`

---

## 🧾 Order
**Table:** `orders`

| Field | Type | Description |
|--------|------|-------------|
| `id` | Long (PK) | Unique order ID |
| `user_id` | Long (FK → users.id) | The user who placed the order |
| `total_price` | Double | Total order value |
| `order_date` | LocalDateTime | When the order was created |
| `status` | String | `PENDING`, `SHIPPED`, `DELIVERED`, etc. |

**Relationships:**
- Many-to-One → `User`
- One-to-Many → `OrderItem`

---

## 📦 OrderItem
**Table:** `order_items`

| Field | Type | Description |
|--------|------|-------------|
| `id` | Long (PK) | Unique order item ID |
| `order_id` | Long (FK → orders.id) | Parent order |
| `product_id` | Long (FK → products.id) | Product in this order |
| `quantity` | Integer | Quantity ordered |
| `subtotal` | Double | Price × Quantity |

**Relationships:**
- Many-to-One → `Order`
- Many-to-One → `Product`

---



