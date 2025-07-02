# 🕒 Attendance Management System

A full-featured Attendance Management System built with **Spring Boot**, allowing Admins, Teachers, and Students to manage and track attendance efficiently.

---

## 📌 Features

- 🧑‍🏫 Admin Panel:
  - User and role management
  - Shift and leave type configuration
  - View reports and analytics

- 👨‍🏫 Teacher Panel:
  - Take and update attendance
  - View class rosters and attendance history

- 👩‍🎓 Student Panel:
  - View attendance status

- 🔒 Authentication & Authorization
- 📊 Dashboard and Reports
- 🗂️ Role-based Access Control

---

## ⚙️ Technologies Used

- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Database**: MySQL
- **Build Tool**: Maven
- **Frontend**: Thymeleaf
- **Others**: Lombok, ModelMapper, JWT (optional)

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17 or above
- Maven 3.6+
- MySQL database

---

### 🔧 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/attendance-management-system.git
   cd attendance-management-system

2. **Configure application.properties**
    spring.datasource.url=jdbc:mysql://localhost:3306/ams
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

3. **Build the project**
   ```bash
   mvn clean install

4. **Run the application**
   ```bash
   mvn spring-boot:run
