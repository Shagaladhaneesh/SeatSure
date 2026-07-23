# 🎬 SeatSure – Full Stack Movie Ticket Booking System

SeatSure is a full-stack movie ticket booking application built using **Spring Boot**, **Angular**, **PostgreSQL**, and **Docker**. It allows users to browse movies, view shows, select seats, book tickets, and manage bookings through a secure JWT-based authentication system.

---

## 🚀 Features

### Authentication
- User Registration
- User Login
- JWT Authentication
- Password Encryption using BCrypt
- Route Protection using Angular Guards
- HTTP Interceptor for JWT Token

### Movie Management
- View available movies
- Movie details
- Genre, Language, Certificate, Duration

### Theatre & Shows
- Browse theatres
- View available shows
- Screen information
- Show timings

### Seat Booking
- Real-time seat availability
- Select multiple seats
- Seat status management
- Booking confirmation

### Booking Management
- Booking history
- Cancel bookings
- Booking status tracking

### Backend Features
- RESTful APIs
- Layered Architecture
- DTO Pattern
- Entity Mapping
- Global Exception Handling
- Data Seeder
- PostgreSQL Database
- Dockerized Backend

### Frontend Features
- Angular Standalone Components
- Responsive UI
- Route Guards
- JWT Interceptor
- Environment Configuration

---

# 🛠️ Tech Stack

## Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JWT
- Docker

## Frontend
- Angular
- TypeScript
- HTML
- CSS

## Database
- PostgreSQL

## Tools
- IntelliJ IDEA
- VS Code
- DBeaver
- Docker Desktop
- Git
- GitHub

---

# 🏗️ Architecture

```

Angular Frontend
│
▼
REST APIs
│
▼
Spring Boot
│
├── Controllers
├── Services
├── Repositories
│
▼
PostgreSQL Database

```

---

# 📂 Project Structure

```

SeatSure
│
├── src/
├── seatsure-ui/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md

```

---

# 🔐 Authentication Flow

1. User Registers
2. Password is encrypted using BCrypt
3. User logs in
4. JWT token is generated
5. Angular stores the token
6. HTTP Interceptor attaches the token to every secured request
7. Spring Security validates the JWT before processing requests

---

# 🗄️ Database Modules

- Users
- Movies
- Theatres
- Screens
- Seats
- Shows
- Show Seats
- Bookings
- Booking Seats

---

# ⚙️ Running with Docker

Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/SeatSure.git
```

Navigate into the project

```bash
cd SeatSure
```

Build and start the application

```bash
docker compose up --build
```

Backend

```
http://localhost:8080
```

Frontend

```
http://localhost:4200
```

---

# 📷 Screenshots

## Login

(Add Screenshot)

## Register

(Add Screenshot)

## Dashboard

(Add Screenshot)

## Movies

(Add Screenshot)

## Shows

(Add Screenshot)

## Seat Selection

(Add Screenshot)

## Booking History

(Add Screenshot)

---

# 🔮 Future Enhancements

- Redis Caching
- Kafka Event-Driven Booking
- Payment Gateway Integration
- Email Notifications
- Role-Based Admin Dashboard
- Prometheus & Grafana Monitoring
- CI/CD with GitHub Actions
- Cloud Deployment

---

# 👨‍💻 Author

**Dhaneesh Pawan**

GitHub: https://github.com/Shagaladhaneesh
