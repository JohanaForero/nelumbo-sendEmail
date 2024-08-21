package com.forero.send_email.infraestructure.adapter;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

@Repository
public class EmailDaoCustomImpl implements EmailDaoCustom {

    private final ReactiveMongoTemplate mongoTemplate;

    public EmailDaoCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Map<String, Object>> findTopEmailsWithCounts() {
        Aggregation aggregation = newAggregation(
                group("email")
                        .count().as("count")
                        .last("email").as("email"),
                sort(Sort.by(Sort.Order.desc("count"))),
                limit(5),
                project("count")
                        .and("email").as("email")
                        .andExclude("_id")
        );

        return mongoTemplate.aggregate(aggregation, "mail", Map.class)
                .map(result -> (Map<String, Object>) result);
    }
}
