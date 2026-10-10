package com.ntu.moviecore.application.material.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardResponse {
    private long id;

    private String wardName;

    private String wardCode;

    private String provinceCode;
}
