# Simple Library Management System

A Spring Boot application to manage books and authors in a library. Supports CRUD operations, sorting, pagination, and cascading deletes.

---

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Sample Payloads](#sample-payloads)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Features

- Add, update, delete, and view authors.
- Add, update, delete, and view books.
- List books/authors with pagination and sorting.
- When an author is deleted, all their books are automatically deleted (cascade).
- Robust exception handling.
- DTO-based API responses with validation.

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok
- ModelMapper

---

## Getting Started

1. **Clone the repository:**
    ```bash
    git clone https://github.com/Astitva2409/SimpleLibraryManagementSystem.git
    cd SimpleLibraryManagementSystem
    ```
2. **Configure Database and Application Properties:**  
   See [Configuration](#configuration) for details.
3. **Build and run:**
    ```bash
    mvn spring-boot:run
    ```
4. **Access the API:**  
   Default URL: `http://localhost:8080`

---

## API Endpoints

This Library Management System exposes comprehensive RESTful APIs for managing books and authors.

### Book APIs

| Method | Endpoint                        | Description                                                                                                                           | Request Params / Body                      |
|--------|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| POST   | `/books`                        | Create a new book.                                                                                                                    | BookDTO (JSON body, validated)             |
| GET    | `/books`                        | Get all books. Supports pagination and sorting.                                                                                       | `sortBy`, `pageNumber` (query params)      |
| GET    | `/books/{id}`                   | Get book details by ID.                                                                                                               | `{id}` (path variable)                     |
| GET    | `/books/searchByTitle`          | Get books by title. Supports pagination and sorting.                                                                                  | `title`, `sortBy`, `pageNumber` (query)    |
| GET    | `/books/searchBooksAfterDate`   | Get all books published after a specific date.                                                                                        | `date` (query param, format: yyyy-MM-dd)   |
| GET    | `/books/by-author`              | Get all books by author name.                                                                                                         | `name` (query param)                       |
| DELETE | `/books/{id}`                   | Delete a book by ID.                                                                                                                  | `{id}` (path variable)                     |
| PUT    | `/books/{id}`                   | Update book details by ID. (Partial update, fields provided in map are updated)                                                       | `{id}` (path variable), updates (JSON map) |

#### Example: Get All Books (Paginated & Sorted)
```
GET /books?sortBy=title&pageNumber=1
```

---

### Author APIs

| Method | Endpoint                       | Description                                                                                                                            | Request Params / Body                      |
|--------|-------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| POST   | `/authors`                    | Create a new author.                                                                                                                   | AuthorDTO (JSON body)                      |
| GET    | `/authors`                    | Get all authors. Supports pagination and sorting.                                                                                      | `sortBy`, `pageNumber` (query params)      |
| GET    | `/authors/{id}`               | Get author details by ID.                                                                                                              | `{id}` (path variable)                     |
| GET    | `/authors/searchByName`       | Get authors by name.                                                                                                                   | `name` (query param)                       |
| DELETE | `/authors/{id}`               | Delete an author by ID. Also deletes all books by this author (cascade delete).                                                        | `{id}` (path variable)                     |
| PUT    | `/authors/{id}`               | Update author details by ID. (Partial update, fields provided in map are updated)                                                      | `{id}` (path variable), updates (JSON map) |

#### Example: Get All Authors (Paginated & Sorted)
```
GET /authors?sortBy=name&pageNumber=0
```

---

## Sample Payloads

### BookDTO (Request Body Example)
Use this format when creating or updating a book (exclude `id`):

```json
{
  "title": "Pride and Prejudice",
  "publishedDate": "1813-01-28",
  "authorId": 3
}
```

**Field Requirements:**
- **title**: Required, 2-50 characters, not blank/null
- **publishedDate**: Required, must not be null or blank, format `YYYY-MM-DD`
- **authorId**: Required, must be a valid author ID (not null)

---

### AuthorDTO (Request Body Example)
Use this format when creating or updating an author (exclude `id`):

```json
{
  "name": "Jane Austen",
  "age": 41,
  "gender": "FEMALE"
}
```

**Field Requirements:**
- **name**: Required, 2-50 characters, not blank/null
- **age**: Required, integer ≥ 18
- **gender**: Optional, should match the values defined in `GenderType` (e.g., "MALE", "FEMALE", "OTHER")

---

## Project Structure

```
src/
  main/
    java/
      com/
        astitva/
          librarysystem/
            A/
              Simple/
                Library/
                  Management/
                    System/
                      controller/
                      service/
                      repository/
                      model/
                      dto/
                      exception/
    resources/
      application.properties
      data.sql
```

---

## Configuration

Configure your database and application properties using `src/main/resources/application.properties`.  
**Sensitive values (username, password) are commented out for security. You should provide these if running locally.**

```properties
spring.application.name=A-Simple-Library-Management-System

# DB configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/libraryDB
#spring.datasource.username=YOUR_USERNAME
#spring.datasource.password=YOUR_PASSWORD
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

#spring.jpa.defer-datasource-initialization=true
#spring.sql.init.mode=always
#spring.sql.init.data-locations=classpath:data.sql
```

You may uncomment and edit the username, password, and initialization options as needed.

---

## Contributing

Contributions are welcome! Please fork the repo and open a pull request for improvements or bug fixes.

---

## License

MIT License

---

## Contact

Maintainer: [Astitva2409](https://github.com/Astitva2409)