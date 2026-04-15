package com.jbyanx.gardenkeep.infrastructure.adapter.in.web.advice;

import com.jbyanx.gardenkeep.domain.exception.BotanicalRuleViolationException;
import com.jbyanx.gardenkeep.domain.exception.CropNotFoundException;
import com.jbyanx.gardenkeep.infrastructure.adapter.in.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CropNotFoundException.class) //cultivo no encontrado
    public ResponseEntity<ErrorResponse> handleNotFound(CropNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "CROP_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BotanicalRuleViolationException.class) //violacion de regla botanica
    public ResponseEntity<ErrorResponse> handleBotanicalViolation(BotanicalRuleViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                "BOTANICAL_RULE_VIOLATION",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class) // Cuando no se encuentra el cultivo
    public ResponseEntity<ErrorResponse> handleNotFound(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                "CROP_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(IllegalStateException.class) // Violación de regla botánica
    public ResponseEntity<ErrorResponse> handleBotanicalViolation(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse(
                "BOTANICAL_RULE_VIOLATION",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

}
