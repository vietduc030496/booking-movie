package com.ntu.moviecore.application.theater.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterNewRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Long wardId;

    @NotBlank
    private String hotline;

    private boolean defaultFlag;
}
