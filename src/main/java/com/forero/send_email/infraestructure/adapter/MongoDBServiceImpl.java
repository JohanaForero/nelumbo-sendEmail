package com.forero.send_email.infraestructure.adapter;

import com.forero.send_email.application.exception.RepositoryException;
import com.forero.send_email.application.service.RepositoryService;
import com.forero.send_email.domain.exception.CodeException;
import com.forero.send_email.domain.model.Email;
import com.forero.send_email.infraestructure.adapter.dao.EmailDao;
import com.forero.send_email.infraestructure.adapter.entity.EmailRecordEntity;
import com.forero.send_email.infraestructure.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoDBServiceImpl implements RepositoryService {
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
                        EmailRecordEntity emailRecord = emailMapper.toEntity(email);
                        emailRecord.setSentDate(LocalDateTime.now());

                        return saveEntity(emailRecord)
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

    private Mono<EmailRecordEntity> saveEntity(final EmailRecordEntity entity) {
        return this.emailDao.save(entity)
                .doFirst(() -> log.info(LOGGER_PREFIX + "[saveEntity] Request {}", entity))
                .doOnSuccess(savedEntity -> log.info(LOGGER_PREFIX + "[saveEntity] Response: {}", savedEntity))
                .onErrorResume(this::handlerError);
    }

    private Mono<EmailRecordEntity> handlerError(final Throwable error) {
        log.error(LOGGER_PREFIX + "[handlerError] Error occurred: {}", error.getMessage());
        return Mono.error(new RepositoryException(CodeException.INTERNAL_SERVER_ERROR, null));
    }

    @Override
    public Flux<Map<String, Object>> getTopFiveRecords() {
        return emailDao.findTopEmailsWithCounts();
    }
}
