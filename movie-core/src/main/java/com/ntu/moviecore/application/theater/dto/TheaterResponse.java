package com.ntu.moviecore.application.theater.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterResponse {

    private long wardId;

    private String wardName;

    private long theaterId;

    private String theaterName;

    private String theaterAddress;

    private boolean isDefault;
}
