package com.ntu.moviecore.domain.theater.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterResponse {

    private long wardId;

    private String wardName;

    private long theaterId;

    private String theaterName;

    private boolean isDefault;
}
