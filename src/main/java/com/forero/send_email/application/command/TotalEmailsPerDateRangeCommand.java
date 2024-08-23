package com.forero.send_email.application.command;

import com.forero.send_email.application.usecase.EmailUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor
public class TotalEmailsPerDateRangeCommand {
    private final EmailUseCase emailUseCase;

    public Mono<Long> execute(final Instant startDate, final Instant endDate) {
        return this.emailUseCase.getEmailStatisticsByRange(startDate, endDate);
    }
}
