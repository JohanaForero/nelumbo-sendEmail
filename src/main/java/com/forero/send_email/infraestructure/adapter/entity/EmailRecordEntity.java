package com.forero.send_email.infraestructure.adapter.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "mail")
public class EmailRecordEntity {
    @Id
    private String id;
    private String email;
    private String plate;
    private String message;
    private String parkingName;

    public EmailRecordEntity(String id, String email, String plate, String message, String parkingName) {
        this.id = id;
        this.email = email;
        this.plate = plate;
        this.message = message;
        this.parkingName = parkingName;
    }
}