package com.ntu.moviecore.domain.theater.dto;

public interface TheaterDb {

    String getProvinceCode();

    String getProvinceName();

    long getWardId();

    String getWardName();

    long getTheaterId();

    String getTheaterName();

    String getTheaterAddress();

    boolean getIsDefault();
}
