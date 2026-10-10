package com.ntu.moviecore.application.theater.dto;

import com.ntu.moviecore.application.material.dto.WardResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProvinceResponse {

    private long provinceId;

    private String provinceName;

    private String provinceCode;

    private List<WardResponse> wards;
}
