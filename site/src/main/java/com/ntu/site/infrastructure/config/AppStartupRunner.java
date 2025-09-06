package com.ntu.site.infrastucture.config;

import com.mservice.shared.utils.LogUtils;
import com.ntu.adminservice.service.material.ProvinceAdminService;
import com.ntu.adminservice.service.material.WardAdminService;
import com.ntu.adminservice.service.movie.MovieAdminService;
import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.customerservice.service.setting.BannerService;
import com.ntu.customerservice.service.theater.TheaterService;
import com.ntu.moviecore.domain.material.dto.WardResponse;
import com.ntu.moviecore.domain.setting.dto.BannerResponse;
import com.ntu.moviecore.domain.theater.dto.ProvinceResponse;
import com.ntu.moviecore.domain.theater.dto.ProvinceTheaterResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ntu.common.constant.CacheAttributeKeyConstant.*;

@Component
@AllArgsConstructor
public class AppStartupRunner implements ApplicationRunner {

    private final TheaterService theaterService;

    private final BannerService bannerService;

    private final WardAdminService wardService;

    private final ProvinceAdminService provinceAdminService;
    private final MovieAdminService movieAdminService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            LogUtils.init();

            List<ProvinceTheaterResponse> theaters = theaterService.getTheaters();
            if (!theaters.isEmpty()) {
                CaffeineCacheUtil.put(PROVINCE_THEATER, theaters);
            }

            List<BannerResponse> banners = bannerService.getBanners(1, 10);
            if (!banners.isEmpty()) {
                CaffeineCacheUtil.put(BANNERS, banners);
            }

            List<WardResponse> wards = wardService.getAllWard();
            if (!wards.isEmpty()) {
                CaffeineCacheUtil.put(WARDS, wards);
            }

            List<ProvinceResponse> provinces = provinceAdminService.getAllProvince();
            if (!provinces.isEmpty()) {
                CaffeineCacheUtil.put(PROVINCES, provinces);
            }

            Map<Long, List<WardResponse>> provinceWardMap = new HashMap<>();
            for (ProvinceResponse province : provinces) {
                provinceWardMap.put(province.getProvinceId(), province.getWards());
            }
            CaffeineCacheUtil.put(PROVINCE_WARDS_MAP, provinceWardMap);

            CaffeineCacheUtil.put(GENRES, movieAdminService.getAllGenres());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
