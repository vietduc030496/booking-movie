package com.ntu.moviecore.domain.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerResponse {

    private long id;

    private String imageUrl;

    private byte priority;
}
