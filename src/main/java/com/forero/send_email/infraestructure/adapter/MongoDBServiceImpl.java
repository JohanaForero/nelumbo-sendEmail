package com.forero.send_email.infraestructure.adapter;

import com.forero.send_email.application.service.EmailService;
import com.forero.send_email.domain.model.Email;
import com.forero.send_email.infraestructure.adapter.dao.EmailDao;
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
    private final EmailDao emailDao;
    private final EmailMapper emailMapper;

    @Override
    public Mono<String> send(final Email email) {

        return Mono.fromCallable(() -> {
            // Simulación de la operación de envío de email
            // Aquí podrías hacer la llamada real a la API externa o servidor SMTP
            System.out.println("Enviando email a: " + email);

            // Simulación de éxito
            return "Email enviado exitosamente a: ";
        });
    }
}
