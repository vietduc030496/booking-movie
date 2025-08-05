package com.ntu.moviecore.domain.material.dto;

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
