package com.forero.send_email.domain.model;

public record Email(Long id, String email, String plate, String message, String parkingName) {
}
