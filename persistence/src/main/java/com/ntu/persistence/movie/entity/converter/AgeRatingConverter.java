package com.ntu.persistence.movie.entity.converter;

import com.ntu.moviecore.domain.movie.model.AgeRating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AgeRatingConverter implements AttributeConverter<AgeRating, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AgeRating attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public AgeRating convertToEntityAttribute(Integer dbData) {
        return dbData != null ? AgeRating.fromCode(dbData) : null;
    }
}
