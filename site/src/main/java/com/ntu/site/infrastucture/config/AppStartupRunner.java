package com.ntu.site.infrastucture.config;

import com.mservice.shared.utils.LogUtils;
import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.customerservice.service.setting.BannerService;
import com.ntu.customerservice.service.theater.TheaterService;
import com.ntu.moviecore.domain.setting.dto.BannerResponse;
import com.ntu.moviecore.domain.theater.dto.ProvinceResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppStartupRunner implements ApplicationRunner {

    private final TheaterService theaterService;

    private final BannerService bannerService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            LogUtils.init();

            List<ProvinceResponse> theaters = theaterService.getTheaters();
            if (!theaters.isEmpty()) {
                CaffeineCacheUtil.put("provinces", theaters);
            }

            List<BannerResponse> banners = bannerService.getBanners(1, 10);
            if (!banners.isEmpty()) {
                CaffeineCacheUtil.put("banners", banners);
            }

            List<String> genres = List.of("Hài hước", "Kinh dị", "Hành động");
            CaffeineCacheUtil.put("genres", genres);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
