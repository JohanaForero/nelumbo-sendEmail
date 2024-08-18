package com.forero.send_email.infraestructure.adapter;

import com.forero.send_email.application.service.EmailService;
import com.forero.send_email.domain.model.Email;
import com.forero.send_email.infraestructure.adapter.dao.EmailDao;
import com.forero.send_email.infraestructure.adapter.entity.EmailRecordEntity;
import com.forero.send_email.infraestructure.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoDBServiceImpl implements EmailService {
    private static final String LOGGER_PREFIX = String.format("[%s] ", MongoDBServiceImpl.class.getSimpleName());
    private final EmailMapper emailMapper;
    private final EmailDao emailDao;

    @Override
    public Mono<String> send(final Email email) {
        return Mono.fromCallable(() -> {
                    log.info(LOGGER_PREFIX + "Enviando email a: {}", email.email());
                    return "Correo Enviado";
                })
                .flatMap(status -> {
                    if ("Correo Enviado".equals(status)) {
                        EmailRecordEntity emailRecordEntity = emailMapper.toEntity(email);
                        return emailDao.save(emailRecordEntity)
                                .thenReturn(status);
                    } else {
                        return Mono.just(status);
                    }
                })
                .onErrorResume(error -> {
                    log.error(LOGGER_PREFIX + "Error al enviar email: {}", error.getMessage());
                    return Mono.just("Error al enviar email: " + error.getMessage());
                });
    }

}
