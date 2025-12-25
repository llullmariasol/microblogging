# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Test Commands

```bash
# Build
mvn clean install              # Build and run all tests
mvn clean package              # Package without tests

# Test
mvn test                       # Run all tests
mvn test -Dtest=UserServiceTest       # Run specific test class
mvn test -Dtest=UserServiceTest#testGetUserById  # Run specific test method

# Run locally (requires MongoDB on localhost:27017)
mvn spring-boot:run

# Run with Docker (starts MongoDB + app)
docker compose up              # App at http://localhost:6868
docker compose down            # Stop containers
```

## Architecture

This is a Java 17 Spring Boot 3.2.1 REST API for microblogging (Twitter-like functionality) using MongoDB.

**Three-layer architecture:**
- `controller/` - REST endpoints with validation (@Valid, Jakarta annotations)
- `service/` - Business logic layer
- `repository/` - Spring Data MongoDB repositories

**Supporting packages:**
- `model/` - MongoDB document entities (User, Tweet)
- `dto/` - Data Transfer Objects for API contracts
- `mapper/` - MapStruct mappers for entity-DTO conversion
- `exception/` - Centralized error handling via @ControllerAdvice

**Key patterns:**
- DTO pattern separates API contracts from internal models
- MapStruct for compile-time mapper generation
- Soft delete for tweets (marked deleted, not removed)
- Bidirectional follow relationships on User model
- Paginated timeline query for tweets from followed users

## Configuration

**Local development:** Uses `application.properties` (MongoDB on localhost:27017, database: microblogging)

**Docker:** Uses `.env` file - app runs on port 6868, MongoDB on port 7017

## Testing

A Postman collection (`microblogging-api.postman_collection.json`) is included with all API endpoints configured.
