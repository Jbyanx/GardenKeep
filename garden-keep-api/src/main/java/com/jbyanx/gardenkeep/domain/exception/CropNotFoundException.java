package com.jbyanx.gardenkeep.domain.exception;

public class CropNotFoundException extends DomainException {
    public CropNotFoundException(String message) {
        super(message);
    }
}