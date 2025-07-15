package com.ntu.domain.authentication;

import lombok.Getter;

@Getter
public enum Gender {

    MALE(1), FEMALE(2), OTHER(3);

    private final int code;

    Gender(int code) {
        this.code = code;
    }
}
