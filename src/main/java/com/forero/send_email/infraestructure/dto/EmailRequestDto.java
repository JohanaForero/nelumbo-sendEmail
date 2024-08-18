package com.forero.send_email.infraestructure.dto;

public record EmailRequestDto(String email, String plate, String message, String parkingName) {
}
