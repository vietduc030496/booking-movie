package com.ntu.moviecore.domain.authentication.entity.converter;

import com.ntu.moviecore.domain.authentication.entity.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Gender attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public Gender convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        for (Gender type : Gender.values()) {
            if (type.getCode() == dbData) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown Gender code: " + dbData);
    }
}
