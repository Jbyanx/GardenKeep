# GardenKeep API

REST API for managing crop watering records, built with **Java 21** and **Spring Boot 3.5** following a **Hexagonal Architecture** (Ports and Adapters).

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.13 |
| Persistence | Spring Data JPA + PostgreSQL 15 |
| Build tool | Maven (Maven Wrapper included) |
| Code generation | Lombok |
| Testing | JUnit 5 + Mockito |
| Coverage | JaCoCo |
| Container | Docker Compose |

---

## Architecture

The project follows the **Hexagonal Architecture** (Ports and Adapters) pattern. Dependencies always point inward: the domain has no framework imports.

```
infrastructure/
  adapter/
    in/web/          ← REST controllers (Spring MVC)
    out/persistence/ ← Database adapters (Spring Data JPA)
  config/            ← Manual @Bean wiring
application/
  port/
    in/              ← Use-case interfaces (driving ports)
    out/             ← Repository interfaces (driven ports)
  service/           ← Use-case implementations
domain/
  model/             ← Pure Java domain entities and enums
```

### Key Classes

| Class | Role |
|---|---|
| `Crop` | Domain entity — holds watering rules and growth-stage state |
| `CropType` | Enum: `ONION`, `GARLIC` |
| `GrowthStage` | Enum: `PHASE_1_SURFACE`, `PHASE_2_DEEP` |
| `RecordWateringUseCase` | Driving port (use-case interface) |
| `CropRepositoryPort` | Driven port (persistence abstraction) |
| `CropWateringService` | Application service — orchestrates the watering flow |
| `CropController` | REST adapter — translates HTTP ↔ use-case |
| `PostgresCropRepositoryAdapter` | Active persistence adapter (PostgreSQL via JPA) |
| `InMemoryCropRepositoryAdapter` | Legacy stub adapter (kept as reference, not wired) |
| `CropEntity` | JPA entity mapped to the `crops` table |
| `CropPersistenceMapper` | Maps between `Crop` domain object and `CropEntity` |
| `JpaCropRepository` | Spring Data JPA interface (`JpaRepository<CropEntity, UUID>`) |
| `BeanConfiguration` | Explicit `@Bean` definitions — selects the active adapter |

---

## Domain Rules

- **Watering is only allowed when the soil is dry at the second knuckle.** Attempting to water with wet soil (`soilDryAtSecondKnuckle = false`) throws an `IllegalStateException`.
- **Growth stages** determine the watering technique:
  - `PHASE_1_SURFACE` — surface moisture to stimulate sprouting.
  - `PHASE_2_DEEP` — deep irrigation (reached when the plant is ≥ 10 cm tall).
- **Sun-bath safety** — `Crop.isSafeForSunBath(LocalTime)` returns `true` only after 15:30 to avoid leaf burn.
- Domain methods receive time as a parameter (e.g., `waterPlant(..., LocalDateTime wateringTime)`) so that tests remain deterministic without mocking the clock.

---

## API

### Water a Crop

```
POST /api/v1/crops/{id}/water
Content-Type: application/json
```

**Path parameter**

| Name | Type | Description |
|---|---|---|
| `id` | UUID | Unique crop identifier |

**Request body**

```json
{
  "soilDryAtSecondKnuckle": true
}
```

**Responses**

| Status | Condition | Body |
|---|---|---|
| `200 OK` | Watering recorded successfully | `"Riego procesado con éxito para el cultivo: {id}"` |
| `500` | Crop not found (`IllegalArgumentException`) | (no global exception mapper yet) |
| `500` | Soil still wet (`IllegalStateException`) | (no global exception mapper yet) |

---

## Getting Started

### Prerequisites

- Java 21
- Docker & Docker Compose

### 1. Start the database

```bash
cd garden-keep-api
docker compose up -d
```

This starts a PostgreSQL 15 container (`gardenkeep-db`) on port `55432`. Set the required environment variables before running Docker Compose:

```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

### 2. Run the application

```bash
cd garden-keep-api
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080` using the `dev` Spring profile by default, which connects to `localhost:55432/gardenkeep_db`.

### 3. Example request

```bash
curl -X POST "http://localhost:8080/api/v1/crops/{cropId}/water" \
  -H "Content-Type: application/json" \
  -d '{"soilDryAtSecondKnuckle": true}'
```

---

## Running Tests

```bash
cd garden-keep-api
./mvnw test
```

Run a specific test class:

```bash
./mvnw -Dtest=CropWateringServiceTest test
./mvnw -Dtest=CropTest test
```

### Test Coverage

JaCoCo generates an HTML report after each test run:

```
garden-keep-api/target/site/jacoco/index.html
```

### Test Suite Summary

| Test class | Tests | What is covered |
|---|---|---|
| `CropTest` | 4 | Domain rules: wet-soil guard, watering-date update, sun-bath safety before/after 15:30 |
| `CropWateringServiceTest` | 3 | Happy path (save called once), crop-not-found guard, domain-rule violation prevents save |
| `GardenKeepApplicationTests` | 1 | Spring context loads without errors |

---

## Project Structure

```
GardenKeep/
├── AGENTS.md                          # Agent/contributor guide
├── README.md                          # This file
└── garden-keep-api/
    ├── docker-compose.yml             # PostgreSQL 15 service definition
    ├── pom.xml                        # Maven build descriptor
    ├── mvnw / mvnw.cmd                # Maven Wrapper
    └── src/
        ├── main/
        │   ├── java/com/jbyanx/gardenkeep/
        │   │   ├── GardenKeepApplication.java
        │   │   ├── domain/model/
        │   │   │   ├── Crop.java
        │   │   │   ├── CropType.java
        │   │   │   └── GrowthStage.java
        │   │   ├── application/
        │   │   │   ├── port/in/RecordWateringUseCase.java
        │   │   │   ├── port/out/CropRepositoryPort.java
        │   │   │   └── service/CropWateringService.java
        │   │   └── infrastructure/
        │   │       ├── adapter/in/web/
        │   │       │   ├── CropController.java
        │   │       │   └── dto/WateringRequest.java
        │   │       ├── adapter/out/persistence/
        │   │       │   ├── PostgresCropRepositoryAdapter.java  ← active
        │   │       │   ├── InMemoryCropRepositoryAdapter.java  ← stub
        │   │       │   ├── entity/CropEntity.java
        │   │       │   ├── mapper/CropPersistenceMapper.java
        │   │       │   └── repository/JpaCropRepository.java
        │   │       └── config/BeanConfiguration.java
        │   └── resources/
        │       ├── application.yml
        │       └── application-dev.yml
        └── test/
            └── java/com/jbyanx/gardenkeep/
                ├── GardenKeepApplicationTests.java
                ├── application/service/CropWateringServiceTest.java
                └── domain/model/CropTest.java
```
