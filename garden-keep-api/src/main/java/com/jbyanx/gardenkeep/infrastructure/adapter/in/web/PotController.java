package com.jbyanx.gardenkeep.infrastructure.adapter.in.web;

import com.jbyanx.gardenkeep.application.port.in.CreatePotUseCase;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.CreatePotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pots")
@RequiredArgsConstructor
public class PotController {
    private final CreatePotUseCase createPotUseCase;

    @PostMapping
    public ResponseEntity<UUID> createPot(@RequestBody CreatePotRequest request) {
        UUID potId = createPotUseCase.execute(request.name(), request.description());
        return new ResponseEntity<>(potId, HttpStatus.CREATED);
    }
}