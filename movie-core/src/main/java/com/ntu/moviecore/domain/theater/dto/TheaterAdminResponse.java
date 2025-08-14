package com.ntu.moviecore.domain.theater.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterAdminResponse {
    private String provinceCode;

    private String provinceName;

    private Long wardId;

    private String wardName;

    private long theaterId;

    private String name;

    private String address;

    private String hotline;

    private boolean defaultFlag;
}
