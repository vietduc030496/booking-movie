package com.ntu.moviecore.domain.elasticsearch.entity;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "movies")
public class MovieElasticSearch {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

}
