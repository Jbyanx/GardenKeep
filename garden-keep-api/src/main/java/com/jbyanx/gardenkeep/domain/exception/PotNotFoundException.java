package com.jbyanx.gardenkeep.domain.exception;

import java.util.UUID;

public class PotNotFoundException extends RuntimeException {
    public PotNotFoundException(UUID id) {
        super("No se encontró la maceta con ID: " + id);
    }
}