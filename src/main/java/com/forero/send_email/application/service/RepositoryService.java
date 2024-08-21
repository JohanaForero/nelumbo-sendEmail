package com.forero.send_email.application.service;

import com.forero.send_email.domain.model.Email;
import reactor.core.publisher.Mono;

public interface RepositoryService {
    Mono<String> send(Email email);
}
