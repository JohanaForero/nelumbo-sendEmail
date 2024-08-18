package com.forero.send_email.application.usecase;

import com.forero.send_email.application.service.EmailService;
import com.forero.send_email.domain.model.Email;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class EmailUseCase {
    private final EmailService emailService;

    public Mono<String> sendEmail(final Email email) {
        return emailService.send(email) // Llamada a la API externa o servicio de correo
                .map(response -> "Email sent successfully")
                .onErrorResume(error -> {
                    // Manejo de errores, devolviendo un mensaje indicando el fallo
                    return Mono.just("Failed to send email: " + error);
                });
    }
}
