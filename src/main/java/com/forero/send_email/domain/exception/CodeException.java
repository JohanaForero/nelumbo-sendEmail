package com.forero.send_email.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeException {
    EMPTY_LIST("No found emails."),
    INTERNAL_SERVER_ERROR("Internal server error"),
    INVALID_PARAMETERS("Invalid request parameters. Please check the %s and try again.");

    private final String messageFormat;
}
