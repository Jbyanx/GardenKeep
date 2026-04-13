package com.jbyanx.gardenkeep.domain.exception;

public class BotanicalRuleViolationException extends RuntimeException {
    public BotanicalRuleViolationException(String message) {
        super(message);
    }
}