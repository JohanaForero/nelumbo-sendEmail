package com.forero.send_email.application.usecase;

import com.forero.send_email.application.service.RepositoryService;
import com.forero.send_email.domain.model.Email;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@AllArgsConstructor
public class EmailUseCase {
    private final RepositoryService repositoryService;

    public Mono<String> sendEmail(final Email email) {
        return repositoryService.send(email);
    }

    public Flux<Map<String, Object>> getTopFiveRecords() {
        return repositoryService.getTopFiveRecords();
    }
}
