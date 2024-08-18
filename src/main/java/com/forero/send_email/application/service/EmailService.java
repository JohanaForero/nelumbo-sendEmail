package com.forero.send_email.application.service;

import com.forero.send_email.domain.model.Email;
import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<String> send(Email email);
}
