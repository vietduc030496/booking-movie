package com.ntu.moviecore.application.theater.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomAdminResponse {

    private long roomId;

    private String name;

    private Long theaterId;

    private String theaterName;

    private Long roomTypeId;

    private String roomTypeName;

    private int capacity;
}
