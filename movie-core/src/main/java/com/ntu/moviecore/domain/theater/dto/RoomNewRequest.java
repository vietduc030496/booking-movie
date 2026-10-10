package com.ntu.moviecore.domain.theater.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomNewRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long theaterId;

    private Long roomTypeId;

    @NotNull
    @Positive
    private Integer capacity;
}
