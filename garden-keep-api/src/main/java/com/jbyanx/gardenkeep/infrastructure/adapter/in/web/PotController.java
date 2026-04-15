package com.jbyanx.gardenkeep.infrastructure.adapter.in.web;

import com.jbyanx.gardenkeep.application.port.in.AddCropToPotUseCase;
import com.jbyanx.gardenkeep.application.port.in.CreatePotUseCase;
import com.jbyanx.gardenkeep.application.port.in.GetPotUseCase;
import com.jbyanx.gardenkeep.application.port.in.WaterPotUseCase;
import com.jbyanx.gardenkeep.domain.model.CropType;
import com.jbyanx.gardenkeep.domain.model.Pot;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.AddCropRequest;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.CreatePotRequest;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.PotResponse;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.WateringRequest;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.mapper.PotWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pots")
@RequiredArgsConstructor
public class PotController {
    private final GetPotUseCase getPotUseCase;
    private final CreatePotUseCase createPotUseCase;
    private final AddCropToPotUseCase addCropToPotUseCase;
    private final PotWebMapper potWebMapper; // Inyectamos el mapper web
    private final WaterPotUseCase waterPotUseCase;

    @PostMapping("/{id}/water")
    public ResponseEntity<String> waterPot(
            @PathVariable UUID id,
            @RequestBody WateringRequest request) {

        waterPotUseCase.execute(id, request.soilDryAtSecondKnuckle());

        return ResponseEntity.ok("Riego procesado con éxito para la maceta y sus cultivos.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PotResponse> getPot(@PathVariable UUID id) {
        Pot pot = getPotUseCase.execute(id);
        return ResponseEntity.ok(potWebMapper.toResponse(pot));
    }

    @PostMapping
    public ResponseEntity<UUID> createPot(@RequestBody CreatePotRequest request) {
        UUID potId = createPotUseCase.execute(request.name(), request.description());
        return new ResponseEntity<>(potId, HttpStatus.CREATED);
    }

    @PostMapping("/{potId}/crops")
    public ResponseEntity<UUID> addCropToPot(
            @PathVariable UUID potId,
            @RequestBody AddCropRequest request) {

        // Convertimos a Enum de forma segura y en mayúsculas
        CropType cropType = CropType.valueOf(request.type().toUpperCase());

        UUID cropId = addCropToPotUseCase.execute(potId, cropType);
        return new ResponseEntity<>(cropId, HttpStatus.CREATED);
    }
}