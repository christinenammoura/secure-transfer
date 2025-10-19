## Secure Funds Transfer Application

A **full-stack banking transfer application** that allows users to securely log in and send money between accounts.  
It includes **JWT authentication**, a **Supabase-hosted PostgreSQL database**, and is deployed on **Render**.

Frontend: Angular

Backend: Java Spring Boot

Database: PostgreSQL on Supabase

Auth: JWT (BCrypt-hashed passwords)

Hosting: Render (backend as Web Service, frontend as Static Site)

----
## Live Demo

-**Frontend:** [https://secure-transfer-ui.onrender.com] https://secure-transfer-ui.onrender.com

-**Backend API:** [https://secure-transfer-0u86.onrender.com] https://secure-transfer-0u86.onrender.com

----

## Sample Credentials (for testing)

Replace with real seeded users in your DB.

-**User A — username:** alice / password: alice123

-**User B — username:** bob / password: bob123

Each user should have one account and sufficient balance to demo transfers.

----

## Features & User Stories

**1) User Authentication**

- Angular login page (username + password, client-side validation)

- Spring Boot /api/auth/login validates credentials against DB

- Passwords stored BCrypt-hashed

- On success, backend returns a JWT

- Frontend stores JWT and redirects to Transfer page

**2) Funds Transfer**

- Transfer page is a protected route (requires JWT)

- Displays “Welcome, username” and current balance

- Form fields: Recipient Account Number, Amount

- Client-side validation (positive amounts, required fields)

- Backend /api/transfers (secured) verifies:

   - Sender from JWT

   - Sufficient funds

   - Recipient account exists

- Debits sender / credits recipient (single transaction)

----

## Data Model (simplified)

**Users**
- id (PK)
- username (unique)
- password (BCrypt hash)

**Accounts**
- id (PK)
- account_number (unique)
- balance (numeric/decimal)
- user_id (FK -> Users.id)

----

## Technologies Used

### Frontend
- **Angular** (TypeScript)
- **RxJS**
- **Bootstrap / CSS**
- **Postman** (for API testing)

### Backend
- **Spring Boot 3 (Java 17)**
- **Spring Security (JWT + BCrypt)**
- **Spring Data JPA**
- **Maven**
- **PostgreSQL (Supabase)**
- **Render (Docker deployment)**

### Database

- **Provider: Supabase**
- **Type: PostgreSQL**
- **Tables: users, accounts, transactions**
- **Passwords: Stored with BCrypt**

----

## Monorepo Layout
/secure-transfer            # Spring Boot backend
  ├─ src/...
  ├─ pom.xml
  └─ Dockerfile

/secure-transfer-ui         # Angular frontend
  ├─ src/...
  ├─ angular.json
  └─ package.json

README.md

## Environment Configuration

- Backend (Spring Boot) — Render Environment Variables

- Set these on the Render Web Service (never commit secrets):

**Key	Example**
SPRING_DATASOURCE_URL	jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME	postgres
SPRING_DATASOURCE_PASSWORD	Root1234
APP_CORS_ALLOWED_ORIGIN	https://secure-transfer-ui.onrender.com
PORT	8080

----

## Local Development
# Prerequisites

- Node 18+ (or 20+), npm
- Java 21 (Temurin)
- Maven 3.9+
- Dbeaver mysql
- Postman for testing API

**1) Backend (Spring Boot)**

- Set local env (or application.properties) with your DB:

spring.datasource.url=jdbc:postgresql://<host>:<port>/<db>?sslmode=require
spring.datasource.username=<user>
spring.datasource.password=<password>

- Run:

cd secure-transfer
./mvn spring-boot:run


- Backend API at: http://localhost:8080

**2) Frontend (Angular)**

- Install + run:

cd secure-transfer-ui

- Run:
  
ng serve

- Frontend at: http://localhost:4200

Local CORS: If needed, allow http://localhost:4200 in your backend CORS (dev only).

----

## Deployment (Render)
- Backend — Web Service (Docker)
- Dockerfile Path: secure-transfer/Dockerfile
- Build Context: secure-transfer

- Frontend — Static Site
- Root Directory: secure-transfer-ui
- Build Command: npm ci && npm run build -- --configuration production
- Publish Directory: dist/secure-transfer-ui/browser
- Redirects/Rewrites: Source: /* Destination: /index.html Action: Rewrite
- API Endpoints
- Base URL (prod): https://secure-transfer-0u86.onrender.com/api

**Auth Controller (/api/auth)**

POST	/login	Authenticate user and return JWT token

**Accounts Controller (/api/accounts)**

GET	/me	Get authenticated user’s account details

**Transfer Controller (/api/transfers)**

POST	/	Create a money transfer (JWT required)

----

## cURL Examples
# Login
curl -X POST https://secure-transfer-0u86.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"<user>","password":"<pass>"}'

# Me (replace TOKEN)
curl https://secure-transfer-0u86.onrender.com/api/accounts/me \
  -H "Authorization: Bearer <TOKEN>"

# Transfer (replace TOKEN)
curl -X POST https://secure-transfer-0u86.onrender.com/api/transfers \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"recipientAccount":"<acct>","amount":10.00}'

----

## Security Notes

- Passwords stored using BCrypt
- JWT auth protects non-auth endpoints
- CORS restricted to deployed frontend origin in production
- Use HTTPS-only origins in production

----

## Troubleshooting

- Frontend calls localhost:8080 in production
- Ensure environment.prod.ts uses the Render backend URL and Angular build uses --configuration production.
- Confirm angular.json fileReplacements swaps environment.ts → environment.prod.ts.

- CORS error from browser
- Set APP_CORS_ALLOWED_ORIGIN=https://secure-transfer-ui.onrender.com on backend service.
- In Spring Security config, ensure http.cors() and a CorsConfigurationSource bean are present.

