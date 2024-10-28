# Gym Application Backend

This is the backend for the Gym Application, built using **Spring Boot**. It provides RESTful API endpoints for managing workouts, exercises, user authentication, and tracking user progress. 

## Features
- **User Authentication**: Registration, login, and JWT-based authentication
- **Exercise and Workout Management**: CRUD operations for workouts and exercises
- **Progress Tracking**: Store and retrieve user exercise data over time
- **Secure and Scalable**: Spring Boot security and modular architecture

## Prerequisites
Make sure you have the following installed before setting up the project:
- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/) (or another database if configured accordingly)
- Currently email verification made to mailtrap, but is not needed for logging in yet

## Getting Started

1. **Clone the repository**
   ```bash
   git clone this repo
   cd to-this-backend-directory
2. Set up the application properties. Template here:
   ```bash
   spring.application.name=workout
   
   MySQL database connection details
   spring.datasource.url=
   spring.datasource.username=
   spring.datasource.password=
   
   Create drop destorys the database on rerun, update will keep the data
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   
   Mailtrap SMTP configuration
   spring.mail.host=
   spring.mail.port=
   spring.mail.username=
   spring.mail.password=
   spring.mail.properties.mail.smtp.auth=
   spring.mail.properties.mail.smtp.starttls.enable=
   
   
   JWT Config
   jwt.secret=your-secret-key
   jwt.accessExpiration=3600000
   jwt.refreshExpiration=604800000
3. Run the application

