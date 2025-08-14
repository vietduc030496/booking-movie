package com.ntu.moviecore.domain.theater.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterNewRequest {
    private String address;

    private Long wardId;

    private String hotline;

    private boolean defaultFlag;
}
