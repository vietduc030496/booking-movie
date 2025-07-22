package com.ntu.moviecore.domain.theater.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProvinceResponse {

    private String provinceCode;

    private String provinceName;

    List<TheaterResponse> theaters;
}
