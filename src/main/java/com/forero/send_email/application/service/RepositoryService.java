package com.forero.send_email.application.service;

import com.forero.send_email.domain.model.Email;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

public interface RepositoryService {
    Mono<String> send(Email email);

    Flux<Map<String, Object>> getTopFiveRecords();

    Mono<Long> getEmailStatistics(Instant startDate, Instant endDate);
}
