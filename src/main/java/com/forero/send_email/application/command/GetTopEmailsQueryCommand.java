package com.forero.send_email.application.command;

import com.forero.send_email.application.usecase.EmailUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.Map;

@RequiredArgsConstructor
public class GetTopEmailsQueryCommand {
    private final EmailUseCase emailUsecase;

    public Flux<Map<String, Object>> execute() {
        return emailUsecase.getTopFiveRecords();
    }
}
