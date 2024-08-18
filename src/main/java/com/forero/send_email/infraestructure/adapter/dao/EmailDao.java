package com.forero.send_email.infraestructure.adapter.dao;

import com.forero.send_email.infraestructure.adapter.entity.EmailEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDao extends ReactiveMongoRepository<EmailEntity, Long> {
}
