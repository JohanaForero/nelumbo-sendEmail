package com.forero.send_email.infraestructure.adapter;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface EmailDaoCustom {
    Flux<Map<String, Object>> findTopEmailsWithCounts();
}
