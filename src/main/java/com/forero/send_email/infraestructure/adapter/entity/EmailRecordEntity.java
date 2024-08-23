package com.forero.send_email.infraestructure.adapter.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder(toBuilder = true)
@Document(collection = "mail")
public class EmailRecordEntity {
    @Id
    private String id;
    private String email;
    private String plate;
    private String message;
    private String parkingName;
    private Instant sentDate;

    public EmailRecordEntity(String id, String email, String plate, String message, String parkingName,
                             Instant sentDate) {
        this.id = id;
        this.email = email;
        this.plate = plate;
        this.message = message;
        this.parkingName = parkingName;
        this.sentDate = sentDate;
    }
}