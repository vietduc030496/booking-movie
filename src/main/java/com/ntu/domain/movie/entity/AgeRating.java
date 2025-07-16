package com.ntu.domain.movie.entity;

import lombok.Getter;

@Getter
public enum AgeRating {

    P(0), T13(1), T16(2), T18(3);

    private final int code;

    AgeRating(int code) {
        this.code = code;
    }

    public static AgeRating fromCode(int code) {
        for (AgeRating type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid age rating code: " + code);
    }
}
