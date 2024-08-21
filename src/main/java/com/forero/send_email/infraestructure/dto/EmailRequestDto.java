package com.forero.send_email.infraestructure.dto;

public record EmailRequestDto(String email, String placa, String mensaje, String parqueaderoNombre) {
}
