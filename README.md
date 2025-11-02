# Inventory Management System (IMS)

A clean, production-conscious backend project built with **Spring Boot**, **JPA/Hibernate**, and **PostgreSQL**.  
This repository demonstrates best practices in API design, DTOs, validation, pagination, projection queries, and error handling.

---

## Features

- **Items & Suppliers CRUD**  
  - Create, Read, Update, Delete operations  
  - Item is linked to a Supplier  

- **DTO & Projection Queries**  
  - Return only the needed fields in API responses  
  - Use `@Query` constructor projections for efficient reads  

- **Pagination Support**  
  - `GET /items` and `GET /suppliers` support paging (page number, page size)  

- **Validation & Exception Handling**  
  - Request-level validations (e.g. non-blank, positive numbers)  
  - Phone number validation using Google’s `libphonenumber` via custom annotation  
  - Centralised exception handling with custom exceptions (e.g. `NotFoundException`)  

- **ModelMapper Integration**  
  - Clean mapping between request DTOs, entities, and response DTOs  

---

## Tech Stack & Dependencies

- **Backend:** Java 17, Spring Boot 3
- **Security:** Spring Security + JWT
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito
- **API Documentation (Upcoming):** Swagger / OpenAPI
- **Reporting (Upcoming):** Excel / CSV / PDF export

---

## Features

 JWT Authentication (Login & Secure APIs)  
 CRUD Operations for Items and Suppliers  
 Pagination and Sorting Support  
 Centralised Exception Handling  
 DTO-based Request/Response  
 Unit Tests with Mockito and SpringBootTest  
 PostgreSQL Integration  
 (Planned) Reporting and Export (Excel/PDF)  
 (Planned) Caching and Kafka Integration  

 ---

 ## Run
You can easily test all endpoints using the provided Postman Collection.
Steps:
Open Postman.
Click Import → Upload Files → select
IMS.postman_collection.json (found in the root of the project).

