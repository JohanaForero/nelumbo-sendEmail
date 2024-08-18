package com.forero.send_email.infraestructure.mapper;

import com.forero.send_email.domain.model.Email;
import com.forero.send_email.infraestructure.dto.EmailRequestDto;
import com.forero.send_email.infraestructure.dto.EmailResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mapping(target = "id", ignore = true)
    Email toModel(EmailRequestDto emailRequestDto);

    EmailResponseDto toDto(Email email);
}
