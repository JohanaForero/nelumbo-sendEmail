package com.forero.send_email.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Email(String id, String email, String plate, String message, String parkingName) {
}
