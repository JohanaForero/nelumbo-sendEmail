package com.forero.send_email.application.command;

import com.forero.send_email.application.usecase.EmailUseCase;
import com.forero.send_email.domain.model.Email;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class SendEmailCommand {
    private final EmailUseCase emailUseCase;

    public Mono<String> execute(final Email email) {
        return emailUseCase.sendEmail(email);
    }
}
