# Complaint Service

REST API do zarządzania reklamacjami, umożliwiające: 
- dodawanie nowych reklamacji
- edycję treści reklamacji
- zwracanie zapisanych wcześniej reklamacji

## Funkcjonalności

- Dodawanie nowej reklamacji
- Zliczanie zgłoszeń unikalnych po productId i reporter
- Edycja treści reklamacji
- Pobieranie wszystkich reklamacji
- Rozpoznawanie kraju zgłoszenia na podstawie IP

## Technologie

- Java 21
- Spring Boot 3+
- Maven
- Spring Data JPA + Hibernate
- PostgreSQL (dev), H2 (test)
- MapStruct – mapowanie DTO ↔ encje
- Spring Retry + blokady optymistyczne – obsługa konkurencji przy inkrementacji zgłoszeń
- AOP - obsługa błędów i logowanie komunikatów
- Spring Validation
- Swagger UI – dokumentacja REST
- Liquibase – migracje schematu bazy
- Lombok


