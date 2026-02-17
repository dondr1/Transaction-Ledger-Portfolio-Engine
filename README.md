# 📈 Transaction Ledger & Portfolio Engine

A RESTful backend service that simulates a financial ledger system, enabling users to deposit funds, buy/sell assets, and compute real-time portfolio balances.

Built using **Java 17, Spring Boot, PostgreSQL, and Docker**, this project demonstrates backend architecture, financial validation logic, and containerized deployment.

---

## 🏗 Architecture Diagram

![Architecture Diagram](/ledger/docs/architecture.png)

---

## 🧠 Architecture Overview

This system follows a layered architecture:

Controller → Service → Repository → Database


### Layer Responsibilities

- **REST Controller**
  - Exposes HTTP endpoints
  - Handles request/response mapping

- **Service Layer**
  - Business logic
  - Validation rules
  - Portfolio calculations
  - Cash balance computation

- **Repository Layer**
  - Spring Data JPA
  - Hibernate ORM
  - Query derivation

- **Database**
  - PostgreSQL
  - Ledger-based persistence model

The system uses an **immutable ledger model**, meaning:
- Transactions are never modified
- Portfolio balances are derived from transaction history
- Invalid states are prevented before persistence

---

## 🚀 Features

- Deposit cash into account
- Buy assets (cash validation enforced)
- Sell assets (holding validation enforced)
- Real-time portfolio calculation
- Zero-quantity assets removed from output
- Custom exception handling (400 responses)
- Unit-tested business logic
- Fully Dockerized backend + PostgreSQL

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA (Hibernate)
- PostgreSQL
- Docker & Docker Compose
- JUnit 5
- Mockito

---

## 📊 API Endpoints

### ➕ Create Transaction

POST /transactions


Example:

```json
{
  "userId": 1,
  "type": "BUY",
  "asset": "AAPL",
  "quantity": 2,
  "price": 100
}
📄 Get User Transactions
GET /transactions/{userId}
📈 Get Portfolio Summary
GET /transactions/portfolio/{userId}
Example Response:

{
  "cashBalance": 750.00,
  "holdings": {
    "AAPL": 1,
    "TSLA": 1
  }
}
🧪 Validation Rules
BUY fails if insufficient cash

SELL fails if insufficient holdings

All errors return HTTP 400 with structured JSON

Example:

{
  "error": "Insufficient cash balance"
}
🐳 Running Locally with Docker
Build JAR
./mvnw clean package -DskipTests
Start Containers
docker compose up --build
Access API
http://localhost:8080/transactions
🧪 Running Unit Tests
./mvnw test
Tests cover:

BUY validation failure

SELL validation failure

Portfolio computation logic

🔮 Future Enhancements
Cloud deployment (AWS / GCP)

CI/CD pipeline

JWT authentication

Pagination for transactions

HTTPS & domain configuration

👨‍💻 Author
Built to demonstrate backend engineering principles including:

Financial ledger modeling

RESTful API design

Containerized infrastructure

Clean architecture separation

Cloud readiness
