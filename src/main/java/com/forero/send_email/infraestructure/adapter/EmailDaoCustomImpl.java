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

    private static final String EMAIL = "email";
    private static final String COUNT = "count";
    private final ReactiveMongoTemplate mongoTemplate;

    public EmailDaoCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Map<String, Object>> findTopEmailsWithCounts() {
        Aggregation aggregation = newAggregation(
                group(EMAIL)
                        .count().as(COUNT)
                        .last(EMAIL).as(EMAIL),
                sort(Sort.by(Sort.Order.desc(COUNT))),
                limit(5),
                project(COUNT)
                        .and(EMAIL).as(EMAIL)
                        .andExclude("_id")
        );

        return mongoTemplate.aggregate(aggregation, "mail", Map.class)
                .map(result -> (Map<String, Object>) result);
    }
}
