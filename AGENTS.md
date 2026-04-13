# AGENTS Guide

## Scope and Entry Point
- Primary module is `garden-keep-api/`; run Maven commands from that directory.
- Java 21 + Spring Boot 3.5 (`garden-keep-api/pom.xml`).

## Architecture (Ports and Adapters)
- Code is organized as hexagonal layers under `com.jbyanx.gardenkeep`:
  - `domain/model`: core rules (`Crop`, `GrowthStage`, `CropType`).
  - `application/port/in`: use-case API (`RecordWateringUseCase`).
  - `application/port/out`: persistence abstraction (`CropRepositoryPort`).
  - `application/service`: orchestration (`CropWateringService`).
  - `infrastructure/adapter/in/web`: REST entrypoint (`CropController`).
  - `infrastructure/adapter/out/persistence`: adapter implementation (`InMemoryCropRepositoryAdapter`).
- Dependency direction is inward: controllers/adapters call ports; domain has no Spring imports.

## Main Data Flow (Watering)
- HTTP `POST /api/v1/crops/{id}/water` in `CropController` accepts `WateringRequest`.
- Controller calls `RecordWateringUseCase.waterCrop(id, soilDryAtSecondKnuckle)`.
- `CropWateringService` loads crop via `CropRepositoryPort.findById` and throws `IllegalArgumentException` if missing.
- Domain rule lives in `Crop.waterPlant(...)`; wet soil (`false`) throws `IllegalStateException`.
- On success, service persists through `CropRepositoryPort.save`.

## Project-Specific Conventions
- Business messages and many comments are Spanish; preserve current language style when touching nearby code.
- Domain methods accept time as a parameter (`Crop.waterPlant(..., LocalDateTime wateringTime)`) to keep tests deterministic.
- Spring wiring is explicit in `infrastructure/config/BeanConfiguration` (manual `@Bean` for service).
- Current persistence adapter is in-memory `Map<UUID, Crop>` with seeded crop ID `00000000-0000-0000-0000-000000000001`.

## Build, Test, and Run
- Use Maven Wrapper:
```bash
cd /home/jbyanx/Desktop/GardenKeep/garden-keep-api
./mvnw test
./mvnw spring-boot:run
./mvnw -Dtest=CropWateringServiceTest test
```
- JaCoCo report is generated on test phase at `garden-keep-api/target/site/jacoco/index.html`.

## Quick Integration Check
- With app running, test the seeded crop:
```bash
curl -X POST "http://localhost:8080/api/v1/crops/00000000-0000-0000-0000-000000000001/water" \
  -H "Content-Type: application/json" \
  -d '{"soilDryAtSecondKnuckle": true}'
```
- Request/response contract is currently simple string responses from `CropController` (no global exception mapper yet).

## Extension Hotspots
- To add real DB persistence, create a new adapter implementing `CropRepositoryPort` under `infrastructure/adapter/out/...` and keep `application/service` unchanged.
- If introducing new use cases, mirror existing pattern: new `application/port/in` interface + service + web adapter translation layer.

