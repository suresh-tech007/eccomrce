## E-commerce System

### Overview
This project is a full-stack e-commerce platform that includes product listings, user authentication, and order management. The frontend is built with React, while the backend is powered by Spring Boot. Two backend options are provided: one using MongoDB and another using MySQL.

---

### Features
1. **User Authentication**
   - Login and signup functionality.
   - JWT-based secure authentication.

2. **Product Management**
   - Browse and search products.
   - Add products to cart.

3. **Order Management**
   - Place orders and view order history.

4. **Backend APIs**
   - RESTful APIs built using Spring Boot for managing users, products, and orders.

5. **Database Options**
   - Backend 1: Uses **MongoDB** for data storage.
   - Backend 2: Uses **MySQL** with Hibernate ORM.

---

### Tech Stack
**Frontend**:
- React.js
- Tailwind CSS

**Backend**:
- Spring Boot
- Java
- Hibernate
- MySQL / MongoDB

---

### Setup Instructions
#### Prerequisites
- Node.js (v14+)
- Java 17+
- MySQL (optional, for backend 2)
- MongoDB (optional, for backend 1)

#### Backend Setup (MySQL)
1. Clone the repository.
   ```bash
   git clone <repo-url>
   cd backend/mysql
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   ```
3. Run the backend application.
   ```bash
   ./mvnw spring-boot:run
   ```

#### Backend Setup (MongoDB)
1. Move to the MongoDB backend folder.
   ```bash
   cd backend/mongo
   ```
2. Configure the MongoDB connection in `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce
   ```
3. Run the backend application.
   ```bash
   ./mvnw spring-boot:run
   ```

#### Frontend Setup
1. Move to the frontend folder.
   ```bash
   cd frontend
   ```
2. Install dependencies.
   ```bash
   npm install
   ```
3. Run the frontend.
   ```bash
   npm run dev
   ```
4. Open your browser at `http://localhost:5173`.

---

### API Endpoints
| Method | Endpoint              | Description                   |
|--------|-----------------------|-------------------------------|
| POST   | `/api/auth/signup`    | User registration             |
| POST   | `/api/auth/login`     | User login                    |
| GET    | `/api/products`       | Fetch all products            |
| POST   | `/api/orders`         | Place an order                |
| GET    | `/api/orders/{userId}`| Get order history for a user  |

---

### Folder Structure
```plaintext
ecommerce-project/
|-- backend/
|   |-- mysql/
|   |   |-- src/main/java/com/example/ecommerce/
|   |   |-- controllers/
|   |   |-- services/
|   |   |-- repositories/
|   |   |-- EcommerceApplication.java
|   |
|   |-- mongo/
|       |-- src/main/java/com/example/ecommerce/
|       |-- controllers/
|       |-- services/
|       |-- repositories/
|       |-- EcommerceApplication.java
|
|-- frontend/
    |-- src/
    |-- components/
    |-- App.jsx
```

---

### Notes
- Backend APIs are integrated with the frontend.
- Two backend options are provided: MongoDB or MySQL.
- **Backend code is included and publicly available.**
