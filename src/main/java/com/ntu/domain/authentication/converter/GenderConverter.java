package com.ntu.domain.authentication.converter;

import com.ntu.domain.authentication.Gender;
import jakarta.persistence.AttributeConverter;

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
