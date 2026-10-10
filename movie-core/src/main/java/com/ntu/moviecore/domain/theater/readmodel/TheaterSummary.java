package com.ntu.moviecore.domain.theater.readmodel;

public interface TheaterDb {

    String getProvinceCode();

    String getProvinceName();

    long getWardId();

    String getWardName();

    long getTheaterId();

    String getTheaterName();

    String getTheaterAddress();

    String getTheaterHotline();

    boolean getIsDefault();
}
