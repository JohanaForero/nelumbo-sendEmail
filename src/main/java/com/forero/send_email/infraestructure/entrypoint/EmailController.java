package com.forero.send_email.infraestructure.entrypoint;

import com.forero.send_email.application.command.SendEmailCommand;
import com.forero.send_email.infraestructure.dto.EmailRequestDto;
import com.forero.send_email.infraestructure.dto.EmailResponseDto;
import com.forero.send_email.infraestructure.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private static final String LOGGER_PREFIX = String.format("[%s] ", EmailController.class.getSimpleName());
    private final SendEmailCommand sendEmailCommand;
    private final EmailMapper emailMapper;

    @PostMapping("/send")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<EmailResponseDto> sendEmail(@RequestBody final EmailRequestDto emailRequestDto) {
        return this.sendEmailCommand.execute(this.emailMapper.toModel(emailRequestDto))
                .doFirst(() -> log.info(LOGGER_PREFIX + "[sendEmail] request {}", emailRequestDto))
                .map(EmailResponseDto::new)
                .doOnSuccess(response -> log.info(LOGGER_PREFIX + "[sendEmail] response {}", response));
    }
}
