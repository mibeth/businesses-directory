package com.nl.icwdirectory.domain;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    INVALID_BUSINESS_ID("The business id can't be null or empty");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
