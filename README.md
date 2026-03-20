# Easy Task - Full Stack Application

Welcome to the **Easy Task** application! This project is a full-stack task management application featuring a **Java Spring Boot** backend, an **Angular** frontend, and a **MySQL** database.

## 🚀 How to Run the Application

There are two ways to run this application: using **Docker Compose** (Recommended) or running it **Locally** (Manual setup).

### Method 1: Using Docker Compose (Recommended)
You can launch the entire stack (Database, Backend, and Frontend) with a single command. 

1. Ensure you have [Docker](https://www.docker.com/) installed.
2. Open a terminal in the root directory (where `docker-compose.yml` is located).
3. Run the following command:
   ```bash
   docker-compose up -d --build
   ```
4. **Access the application:**
   - **Frontend UI:** [http://localhost:4200](http://localhost:4200)
   - **Backend API:** [http://localhost:8080](http://localhost:8080)
   - **Database (via external tool):** `localhost:3307`

### Method 2: Running Locally (Without Docker)
If you prefer not to build the `docker-compose.yml` file and want to run everything manually, you update the application to connect to your local MySQL instance.

#### 1. Database Setup
Ensure you have a local MySQL server running on port `3306`. Create a database schema before starting.
- **Database Name:** `easy_task`
- **Username:** `root`
- **Password:** `1234`

#### 2. Backend Configuration Update
Before running the backend, you must update the database connection URL in the properties file: `backend/src/main/resources/application.properties`.

**Comment out the Docker URL:**
```properties
# spring.datasource.url=jdbc:mysql://mysql-db:3306/easy_task?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```
**Uncomment the Localhost URL:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/easy_task
```

Run the backend via Maven:
```bash
cd backend
mvn spring-boot:run
```

#### 3. Frontend Setup
Make sure you have Node.js (v20+ or v22+) and Angular CLI installed.
```bash
cd frontend
npm install
npm start
```

---

## 🗄️ Database Setup Details

The application utilizes **MySQL 8.0**. 

- **Docker Mode:** 
  The database schema and tables are automatically configured. Any initialization scripts present in the `init-db` folder will be executed upon creation. The database data persists across restarts via the `mysql-data` volume.
  
- **Host Port (Docker):** `3307` 
- **Internal Container Port:** `3306`

---

## 🔐 Credentials & Security

The application utilizes JWT (JSON Web Tokens) for securing endpoints and authentication.

- **JWT Secret Key:** `NHKmZXMnIUvXcOll@!#11k@jUikjYio.LOL.1234567890ABCDEF`
*(Note: For production environments, this should be stored safely in an environment variable `application-prod.properties` rather than hardcoded.)*

---

## 📸 Screenshots
Home Page
<img width="1918" height="1021" alt="image" src="https://github.com/user-attachments/assets/bc3c4dc1-87d5-4ca6-b32a-06635c674cf0" />
Login Page
<img width="1918" height="1017" alt="image" src="https://github.com/user-attachments/assets/a5c0e32b-417d-41d9-a77f-171bf114de66" />
Register Page
<img width="1918" height="1021" alt="image" src="https://github.com/user-attachments/assets/9597c617-62a1-47de-b8b1-d7c40cda6226" />
Dashboard Page
<img width="1918" height="1017" alt="image" src="https://github.com/user-attachments/assets/60567564-922e-4a63-ba6f-3c7ee7d217b8" />
View Task Page
<img width="1918" height="1018" alt="image" src="https://github.com/user-attachments/assets/48a5b041-b02a-4ff3-be62-5ddc9d0c899b" />
New Task Page
<img width="1918" height="1022" alt="image" src="https://github.com/user-attachments/assets/3507a351-af30-4f39-92ee-0e690eb9ae68" />
Update Task Page
<img width="1918" height="1018" alt="image" src="https://github.com/user-attachments/assets/df75382b-3363-4ff6-bf4b-8d65b72a75fd" />


<!-- Example:
![Login Screen](./assets/login-screenshot.png)
![Dashboard](./assets/dashboard-screenshot.png)
-->

---
*Thank you for reviewing my work!*
