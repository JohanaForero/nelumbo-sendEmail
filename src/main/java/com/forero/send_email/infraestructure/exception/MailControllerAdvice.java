package com.forero.send_email.infraestructure.exception;

import com.forero.send_email.application.exception.CoreException;
import com.forero.send_email.domain.exception.CodeException;
import com.forero.send_email.domain.exception.MailExceptionDomain;
import com.forero.send_email.infraestructure.dto.ErrorObjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class MailControllerAdvice {
    private static final String LOGGER_PREFIX = String.format("[%s] ", MailControllerAdvice.class.getSimpleName());

    private static final Map<CodeException, HttpStatus> HTTP_STATUS_BY_CODE_EXCEPTION = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CodeException.INVALID_PARAMETERS, HttpStatus.BAD_REQUEST),
            new AbstractMap.SimpleEntry<>(CodeException.EMPTY_LIST, HttpStatus.BAD_REQUEST),
            new AbstractMap.SimpleEntry<>(CodeException.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    );

    @ExceptionHandler(CoreException.class)
    public Mono<ResponseEntity<ErrorObjectDto>> handlerCoreException(final CoreException coreException) {
        return Mono.just(coreException)
                .flatMap(coreExceptionS -> {
                    final CodeException codeException = coreException.getCodeException();
                    final ErrorObjectDto errorResponseDto = ErrorObjectDto.builder()
                            .code(codeException.name())
                            .message(coreExceptionS.getMessage())
                            .build();
                    final HttpStatus status = HTTP_STATUS_BY_CODE_EXCEPTION
                            .getOrDefault(codeException, HttpStatus.NOT_EXTENDED);
                    return Mono.just(new ResponseEntity<>(errorResponseDto, status));
                });
    }

    @ExceptionHandler(MailExceptionDomain.class)
    public Mono<ResponseEntity<ErrorObjectDto>> handlerUserDomainException(final MailExceptionDomain mailExceptionDomain) {
        return Mono.just(mailExceptionDomain)
                .flatMap(mailExceptionDomain1 -> {
                    final CodeException codeException = mailExceptionDomain.getCodeException();
                    final ErrorObjectDto errorResponseDto = ErrorObjectDto.builder()
                            .code(codeException.name())
                            .message(mailExceptionDomain1.getMessage())
                            .build();
                    final HttpStatus status = HTTP_STATUS_BY_CODE_EXCEPTION
                            .getOrDefault(codeException, HttpStatus.NOT_EXTENDED);
                    return Mono.just(new ResponseEntity<>(errorResponseDto, status));
                });
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorObjectDto>> handlerException(final Exception exception) {
        return Mono.just(CodeException.DB_INTERNAL)
                .doOnNext(codeException -> log
                        .error(LOGGER_PREFIX + "[handlerException] {}", exception.getMessage(), exception))
                .flatMap(codeException -> Mono.just(this.buildErrorResponseEntity(codeException)));
    }

    @ExceptionHandler(UncategorizedMongoDbException.class)
    public Mono<ResponseEntity<ErrorObjectDto>> handlerUncategorizedMongoDbException(
            final UncategorizedMongoDbException uncategorizedMongoDbException) {
        return Mono.just(CodeException.DB_INTERNAL)
                .flatMap(codeException -> Mono.just(this.buildErrorResponseEntity(codeException)));
    }

    private ResponseEntity<ErrorObjectDto> buildErrorResponseEntity(final CodeException codeException) {
        final ErrorObjectDto errorResponseDto = ErrorObjectDto.builder()
                .code(codeException.name())
                .message(codeException.getMessageFormat())
                .build();
        final HttpStatus status = HTTP_STATUS_BY_CODE_EXCEPTION
                .getOrDefault(codeException, HttpStatus.NOT_EXTENDED);
        return new ResponseEntity<>(errorResponseDto, status);
    }
}
