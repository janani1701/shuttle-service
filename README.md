# Shuttle-Boot Application

**Backend Application for Managing Student Shuttle Services**

## Technologies Used

- **Back-end Framework:** Spring
- **Programming Language:** Java 1.8
- **Database Tool:** MySQL Workbench
- **API Testing:** Postman
- **Project Scope:** Maven Module
- **Infrastructure:** Running on localhost at port 8085

## Controllers

### AuthController

- **Root API:** `/auth/api/`
- Endpoints:
  - `POST /signin`: Authenticate a user
  - `POST /signup`: Register a user
  - `GET /username`: Get user info with a bearer token

### ShuttleController

- Endpoints:
  - `POST /addPassenger`: Add a passenger (only for drivers/admins)
  - `POST /shuttleLocation`: Update shuttle location (only for drivers/admins)
  - `POST /checkETA`: Check shuttle ETA (only for drivers/admins)

### TestController

- **Root API:** `/api/test/`
- Endpoints for testing roles:
  - `GET /all`: Accessible by all
  - `GET /user`: Accessible by students, admins, and drivers
  - `GET /driver`: Accessible by drivers
  - `GET /admin`: Accessible by admins

## Models

- **DriverShuttle:** Table for driver shuttle details
- **ERole:** Enumeration for user roles
- **Role:** Table for roles and IDs
- **StudentShuttle:** Table for student shuttle details
- **User:** Table for user information

## How to Run the Code

1. Right-click on the `main` method in `SpringBootSecurityJwtApplication` class.
2. Run the project, which will listen on port 8085.


