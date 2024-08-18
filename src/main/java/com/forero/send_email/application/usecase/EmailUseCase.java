package com.forero.send_email.application.usecase;

import com.forero.send_email.application.service.EmailService;
import com.forero.send_email.domain.model.Email;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class EmailUseCase {
    private final EmailService emailService;

    public Mono<String> sendEmail(final Email email) {
        return emailService.send(email);
    }
}
