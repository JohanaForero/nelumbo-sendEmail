package com.forero.send_email.infraestructure.entrypoint;

import com.forero.send_email.application.command.GetTopEmailsQueryCommand;
import com.forero.send_email.application.command.SendEmailCommand;
import com.forero.send_email.application.command.TotalEmailsPerDateRangeCommand;
import com.forero.send_email.infraestructure.dto.EmailRequestDto;
import com.forero.send_email.infraestructure.dto.EmailResponseDto;
import com.forero.send_email.infraestructure.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailController {
    private static final String LOGGER_PREFIX = String.format("[%s] ", EmailController.class.getSimpleName());
    private final SendEmailCommand sendEmailCommand;
    private final GetTopEmailsQueryCommand getTopEmailsQueryCommand;
    private final TotalEmailsPerDateRangeCommand totalEmailsPerDateRangeCommand;
    private final EmailMapper emailMapper;

    @PostMapping("/send")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<EmailResponseDto> sendEmail(@RequestBody final EmailRequestDto emailRequestDto) {
        return this.sendEmailCommand.execute(this.emailMapper.toModel(emailRequestDto))
                .doFirst(() -> log.info(LOGGER_PREFIX + "[sendEmail] request {}", emailRequestDto))
                .map(EmailResponseDto::new)
                .doOnSuccess(response -> log.info(LOGGER_PREFIX + "[sendEmail] response {}", response));
    }

    @GetMapping("/top")
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<Map<String, Object>> getTopEmails() {
        return this.getTopEmailsQueryCommand.execute()
                .doFirst(() -> log.info(LOGGER_PREFIX + "[getTopEmails] Request"))
                .doOnComplete(() -> log.info(LOGGER_PREFIX + "[getTopEmails] Completed"));
    }

    @GetMapping("/total")
    public Mono<Long> receiveEmailsSentByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate) {
        final Instant startInstant = startDate.toInstant();
        final Instant endInstant = endDate.toInstant();
        return this.totalEmailsPerDateRangeCommand.execute(startInstant, endInstant)
                .doFirst(() -> log.info("[receiveEmailsSentByDateRange] Request startDate: {} and endDate: {}",
                        startDate, endDate))
                .doOnSuccess(count -> log.info("[receiveEmailsSentByDateRange] Response: {}", count));
    }
}
