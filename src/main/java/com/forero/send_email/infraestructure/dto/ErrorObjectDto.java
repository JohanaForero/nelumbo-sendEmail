package com.forero.send_email.infraestructure.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record ErrorObjectDto(String code, String message) {
}
