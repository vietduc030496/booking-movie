package com.ntu.moviecore.domain.theater.dto;

import com.ntu.moviecore.domain.material.dto.WardResponse;
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
