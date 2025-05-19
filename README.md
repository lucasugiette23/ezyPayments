# ezyPayments

**ezyPayments** is a robust and fault-tolerant payment processing microservice built with **Spring Boot**, **PostgreSQL**, and **RabbitMQ**. It securely processes credit card payments, stores them with encryption, and notifies registered webhooks via asynchronous messaging and DLQ (Dead Letter Queue) for resiliency.

## ✨ Features

- ✅ Process and encrypt card payments
- ✅ Asynchronous webhook notifications using RabbitMQ
- ✅ Retry and DLQ support for fault tolerance
- ✅ REST API for creating payments and registering webhooks
- ✅ PostgreSQL persistence
- ✅ Modular and testable architecture

## 🧱 Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring AMQP (RabbitMQ)
- PostgreSQL
- Docker & Docker Compose
- JUnit + Mockito for testing

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- RabbitMQ and PostgreSQL (can be started via Docker)

### Clone the project

```bash
git clone https://github.com/your-username/ezyPayments.git
cd ezyPayments
```

---

### 🔧 Run the services

Use Docker Compose to start **PostgreSQL** and **RabbitMQ** locally:

```bash
docker-compose -f docker-compose.yml up
```

Then start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

---

## 📬 API Endpoints

### 🔐 Payment Endpoint

```http
POST /api/payments
```

**Body:**
```json
{
  "cardNumber": "4111111111111111",
  "cvv": "123",
  "expiry": "12/25",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2025-05-19T12:34:56"
}
```

---

### 🔗 Register Webhook

```http
POST /api/webhooks
```

**Body:**
```json
{
  "clientId": "1",
  "eventType": "payment.created",
  "endpoint": "https://your-api.com/webhook"
}
```

---

## ⚙️ Configuration

In `application.properties`:

```properties
spring.application.name=ezyPayments

spring.datasource.url=jdbc:postgresql://localhost:5432/ezypayments
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

rabbitmq.exchange=payment.exchange
rabbitmq.routing-key=payment.notify
webhook.queue.name=payment.webhook.queue
```

---

## 📦 Message Flow

1. A payment is created and saved (with encryption).
2. Registered webhooks for the `clientId` are fetched.
3. Each webhook is sent to a RabbitMQ queue.
4. `WebhookListener` consumes messages and sends HTTP requests.
5. If delivery fails, it’s retried and eventually goes to the DLQ.

---

## 🧪 Tests

Run unit tests with:

```bash
./mvnw test
```

Coverage includes:

- Payment processing logic
- Webhook notification flow
- Repository mocking
- Encryption verification

---

## 📁 Project Structure

```
com.puzzle.ezypayments
├── config
├── controller
├── dto
├── entity
├── messaging
├── repository
├── service
└── util
```

---

## 🐳 Docker Compose (optional)

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: ezypayments-postgres
    environment:
      POSTGRES_DB: ezypayments
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"

  rabbitmq:
    image: rabbitmq:3-management
    container_name: ezypayments-rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
```

---

## 🧠 Improvements

- Add authentication/authorization
- Frontend integration (React/TypeScript)
- Retry configuration via annotations (`@Retryable`)
- Advanced webhook filtering and validation

---

## 📫 Contact

Developed by **Lucas** — for technical tests or real-world usage.

> This is a technical challenge project showcasing asynchronous processing and resilient webhook delivery.
