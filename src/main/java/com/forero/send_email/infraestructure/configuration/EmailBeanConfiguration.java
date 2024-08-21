package com.forero.send_email.infraestructure.configuration;

import com.forero.send_email.application.command.SendEmailCommand;
import com.forero.send_email.application.service.RepositoryService;
import com.forero.send_email.application.usecase.EmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailBeanConfiguration {
    @Bean
    public EmailUseCase emailUseCase(final RepositoryService repositoryService) {
        return new EmailUseCase(repositoryService);
    }

    @Bean
    public SendEmailCommand sendEmailCommand(final EmailUseCase emailUseCase) {
        return new SendEmailCommand(emailUseCase);
    }
}
