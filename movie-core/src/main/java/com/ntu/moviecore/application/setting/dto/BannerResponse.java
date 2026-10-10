package com.ntu.moviecore.application.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerResponse {

    private long id;

    private String imageUrl;

    private byte priority;
}
