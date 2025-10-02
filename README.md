# Inventory Management System (IMS)

A clean, production-conscious backend project built with **Spring Boot**, **JPA/Hibernate**, and **PostgreSQL**.  
This repository demonstrates best practices in API design, DTOs, validation, pagination, projection queries, and error handling.

---

## Features (Phase 1)

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

| Layer                 | Technology                                              |
|-----------------------|---------------------------------------------------------|
| Language & Platform   | Java 17, Spring Boot 3.x                                |
| Database              | PostgreSQL                                              |
| ORM / Data            | Spring Data JPA, Hibernate                              |
| Validation / Mapping  | Hibernate Validator, ModelMapper, Google libphonenumber |
| Build & Dev           | Maven, Lombok                                           |
-----------------------------------------------------------------------------------


