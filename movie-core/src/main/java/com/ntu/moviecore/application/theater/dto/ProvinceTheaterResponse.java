package com.ntu.moviecore.application.theater.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProvinceTheaterResponse {

    private String provinceCode;

    private String provinceName;

    private List<TheaterResponse> theaters;
}
