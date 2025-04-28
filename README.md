# eValeBike Project

## 📌 Project Overview
This is a Spring Boot application using Gradle and PostgreSQL. The project is set up with separate Spring profiles for **development** and **production**. The development environment includes a **Dockerized PostgreSQL database**.

---

## 👥 Team 4 Members
- **Abir Belhadj**
- **Viktória Dobišová**
- **Nang Cherry Naw**
- **Deren Ozen**
- **Lilit Tsugunyan**

---

## 🔧 Requirements
To build and run the application, you need:
- **Java 21 (JDK 21)** → Ensure your Java version is 21+
- **Gradle with Kotlin DSL** → The project uses `Gradle` (`build.gradle.kts`)
- **Docker** → Required for running PostgreSQL in a container

---

## 📦 Dependencies
The following dependencies are included in the project:
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **PostgreSQL Driver**
- **Spring Boot DevTools**
- **Docker for PostgreSQL**

## 🚀 Build & Run Instructions

### **🔨 Build the Project**
Before running the application, ensure all dependencies are installed and the project is built.
```sh
  ./gradlew build
```
### **▶️ Run the Application**
```sh
  ./gradlew bootRun
```
### **Start PostgreSQL Database (Docker)**

Ensure Docker is installed and running. Then, start the PostgreSQL container:
```sh
  docker-compose up -d
```
To stop the database
```sh
  docker-compose down
```

## 🔧 Configuration & Environment Profiles

This project includes two Spring profiles:

### 🛠 Development (`application-dev.properties`)
- Uses a **PostgreSQL database** running in a **Docker container**.

```properties
spring.application.name=eValeBike
spring.datasource.url=jdbc:postgresql://localhost:5436/eValeBike
spring.datasource.username=user
spring.datasource.password=password

# Enable SQL logging for debugging
spring.jpa.show-sql=true

```

### 🚀 Production (`application-prod.properties`)
- Placeholder for **production database settings** for now it is the same as development profile.

```properties
spring.application.name=eValeBike
spring.datasource.url=jdbc:postgresql://localhost:5436/eValeBike
spring.datasource.username=user
spring.datasource.password=password

# Disable SQL logging for performance
spring.jpa.show-sql=false

```

By default, the **development profile** is active. You can switch profiles by modifying:

```properties
spring.application.name=eValeBike
spring.datasource.url=jdbc:postgresql://localhost:5436/eValeBike
spring.datasource.username=user
spring.datasource.password=password

spring.profiles.active=dev

spring.jpa.hibernate.ddl-auto=update

spring.jpa.open-in-view=false

logging.level.org.springframework.security=debug

```
### **📂 Project Structure**

```
📦 project-root
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┗ 📂integration4
 ┃ ┃ ┃    ┗ 📂evalebike
 ┃ ┃ ┃       ┗ 📜 MainApplication.java
 ┃ ┃ ┣ 📂 resources
 ┃ ┃ ┃ ┣ 📜 application.properties
 ┃ ┃ ┃ ┣ 📜 application-dev.properties
 ┃ ┃ ┃ ┗ 📜 application-prod.properties
 ┣ 📜 build.gradle.kts
 ┣ 📜 docker-compose.yml
 ┣ 📜 README.md
 ┗ 📜 .gitignore
```