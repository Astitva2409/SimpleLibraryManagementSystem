# A Simple Library Management System

A Spring Boot application for managing books and authors in a library.  
It supports CRUD operations for books and authors, uses DTOs for data transfer, and demonstrates entity relationships and validation.

---

## Features

- Add, update, delete, and retrieve books and authors
- RESTful API endpoints
- Data validation using annotations
- DTO-based requests/responses
- Relational mapping (`Book` ↔ `Author`)
- Sorting and Pagination
- Exception handling

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA & Hibernate
- Maven
- H2/PostgreSQL (configurable)
- ModelMapper (for DTO conversions)

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/Astitva240901/A-Simple-Library-Management-System.git
   cd A-Simple-Library-Management-System
   ```

2. **Build and run the project**
   ```sh
   mvn spring-boot:run
   ```

3. **API will be available at**
   ```
   http://localhost:8080/
   ```

---

## API Usage Examples

### Create an Author

**POST /authors**
```json
{
  "name": "Haruki Murakami",
  "age": 45,
  "gender": "MALE"
}
```

### Create a Book

**POST /books**
```json
{
  "title": "Kafka on the Shore",
  "publishedDate": "2002-09-12",
  "authorId": 1
}
```

---

## Configuration

Database settings are in `src/main/resources/application.properties`.  
Example for H2:
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.properties.hibernate.dialect=

```

---

## Project Structure

```
src/
  main/
    java/
      com/
        example/
          library/
            controller/
            service/
            dto/
            entity/
            repository/
    resources/
      application.properties
```

---

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes
4. Push and create a Pull Request

---

## License

This project is licensed under the MIT License.
