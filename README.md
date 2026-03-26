# 🎬 KMDB: Java Spring Boot Movie Database API

REST API for managing movies, actors, and genres with JWT-based authentication.

---

## 📋 Project Overview

The KMDB is a secure RESTful web service built using **Java** and **Spring Boot**, offering rich features for managing movie records, their associated actors, and genres. It includes many-to-many relationships, full CRUD operations, advanced search/filtering capabilities, and secure access via JWT tokens.

### 🔑 Key Features

- **JWT Authentication** – Secure access with login-protected endpoints  
- **CRUD Operations** – Manage movies, actors, and genres  
- **Relationship Management** – Many-to-many support with proper linking  
- **Search & Filtering** – By title, genre, year, and actor  
- **Batch Operations** – Create movies with multiple actors and genres in a single call  
- **Safe Deletion** – Prevent deletion of linked entities unless forced  
- **Validation** – Strict field validation with helpful error messages  
- **Pagination & Sorting** – All list endpoints support pagination  
- **Global Error Handling** – Consistent and structured API error responses  

---

## 🛠️ Setup and Installation

### ✅ Prerequisites

- Java 21+  
- Maven 3.6+  
- PostgreSQL database running and configured  
- Runs on **port 8080** by default  

### ⚙️ Installation Steps

1. **Clone the Repository:**

```bash
git clone https://gitea.kood.tech/mikkmerila/kmdb.git
cd kmdb/movies-api
```

2. **Run the App:**

```bash
mvn clean spring-boot:run
```

3. **Access API Docs / Interface:**

```
http://localhost:8080
```

---

## 🔐 Authentication

All endpoints except GET under `/api/auth/**` require a **JWT token**. You must first log in to receive a valid token.

### 🔑 Login

**POST** `/api/auth/login`

```json
{
  "username": "kooduser",
  "password": "mikkmerila"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 🔐 Using the Token in Postman

1. Go to Authorization tab → Type: **Bearer Token**  
2. Paste the token you received from `/auth/login`  
3. All secured endpoints will now accept requests  

---

## 📖 API Usage

### 📌 Base URL

```
http://localhost:8080
```

### 🎥 Movies Endpoints

| Method | Endpoint                               | Description                       |
|--------|--------------------------------------|----------------------------------|
| GET    | `/api/movies`                        | Get all movies (paginated)        |
| GET    | `/api/movies/{id}`                   | Get movie by ID                   |
| POST   | `/api/movies`                        | Create a new movie                |
| PATCH  | `/api/movies/{id}`                   | Update movie fields               |
| DELETE | `/api/movies/{id}?force=true`        | Force delete (unlink first)       |
| GET    | `/api/movies/search?title={title}`  | Search movies by title            |
| GET    | `/api/movies?genreId={id}`           | Filter by genre                  |
| GET    | `/api/movies?year={year}`            | Filter by release year            |
| GET    | `/api/movies?actorId={id}`           | Filter by actor                  |

### 🎭 Actors Endpoints

| Method | Endpoint                               | Description                       |
|--------|--------------------------------------|----------------------------------|
| GET    | `/api/actors`                        | Get all actors (paginated)        |
| GET    | `/api/actors/{id}`                   | Get actor by ID                  |
| GET    | `/api/actors?name={name}`            | Search actors by name             |
| POST   | `/api/actors`                        | Create new actor                 |
| PATCH  | `/api/actors/{id}`                   | Update actor                    |
| DELETE | `/api/actors/{id}?force=true`        | Force delete (remove from movies) |
| GET    | `/api/movies?actorId={id}`           | List all movies an actor appeared in |

### 🏷️ Genres Endpoints

| Method | Endpoint                               | Description                       |
|--------|--------------------------------------|----------------------------------|
| GET    | `/api/genres`                       | Get all genres (paginated)        |
| GET    | `/api/genres/{id}`                  | Get genre by ID                  |
| POST   | `/api/genres`                       | Create new genre                |
| PATCH  | `/api/genres/{id}`                  | Update genre                   |
| DELETE | `/api/genres/{id}?force=true`        | Force delete (unlink from movies) |
| GET    | `/api/movies?genreId={id}`           | List all movies in the genre     |

---

## 🔁 Relationship Management

| Method | Endpoint                                 | Description                     |
|--------|------------------------------------------|--------------------------------|
| GET    | `/api/movies/{id}/actors`                | Get all actors in a movie       |
| PATCH  | `/api/movies/{id}/actors`                | Update movie’s full actors list |
| PATCH  | `/api/movies/{id}/genres`                | Update movie’s full genres list |
| POST   | `/api/movies/{movieId}/actors/{actorId}` | Add actor to movie              |
| DELETE | `/api/movies/{movieId}/actors/{actorId}` | Remove actor from movie         |
| POST   | `/api/movies/{movieId}/genres/{genreId}` | Add genre to movie              |

---

## 📝 Example Requests

### Create a Movie

```http
POST /api/movies
Content-Type: application/json
Authorization: Bearer <token>
```

```json
{
  "title": "Inception",
  "releaseYear": 2010,
  "duration": 148
}
```

### Create a Movie with Actors and Genres

```json
{
  "title": "The Matrix",
  "releaseYear": 1999,
  "duration": 136,
  "genreIds": [1, 2],
  "actorIds": [1, 2]
}
```

### Add a New Actor

```http
POST /api/actors
Content-Type: application/json
Authorization: Bearer <token>
```

```json
{
  "name": "Leonardo DiCaprio",
  "birthDate": "1974-11-11"
}
```

### Update Movie Info

```http
PATCH /api/movies/1
Content-Type: application/json
Authorization: Bearer <token>
```

```json
{
  "title": "The Matrix Reloaded",
  "duration": 138
}
```

---

## 🔍 Search & Pagination

### Filter Examples

- `GET /api/movies/search?title=matrix`  
- `GET /api/movies?genreId=1`  
- `GET /api/movies?year=1999`  
- `GET /api/movies?actorId=2`  
- `GET /api/actors?name=tom`  

### Pagination Parameters

All list endpoints support:

| Parameter | Description                      |
|-----------|----------------------------------|
| `page`    | Page number (default: 0)         |
| `size`    | Items per page (default: 10)     |
| `sort`    | Field to sort by (`title`, etc.) |
| `dir`     | Sort direction: `asc` or `desc`  |

Example:

```http
GET /api/movies?page=1&size=5&sort=releaseYear&dir=desc
```

---

## 🚨 Error Handling

### ❌ Validation Error (400)

```json
{
  "timestamp": "2025-07-15T10:00:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input",
  "path": "/api/movies",
  "fieldErrors": {
    "title": "Title is required",
    "releaseYear": "Release year must be provided"
  }
}
```

### ❌ Resource Not Found (404)

```json
{
  "timestamp": "2025-07-15T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Movie not found with ID: 999",
  "path": "/api/movies/999"
}
```

---

## 🔧 Tech Stack

- Java 21+  
- Spring Boot 3.5.0  
- Spring Security with JWT  
- Spring Data JPA + Hibernate  
- PostgreSQL  
- Maven  

---

## 📬 Contact

For questions or contributions, reach out via Git or the course platform.  
Made by Mikk Merila