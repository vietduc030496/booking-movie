package com.ntu.customerservice.application.setting.service;

import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.moviecore.application.setting.dto.BannerResponse;
import com.ntu.persistence.setting.repository.BannerJpaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ntu.common.constant.CacheAttributeKeyConstant.BANNERS;

@Service
@AllArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final ModelMapper modelMapper;

    public List<BannerResponse> getBanners(int page, int size) {
        if (CaffeineCacheUtil.containsKey(BANNERS)) {
            return (List<BannerResponse>) CaffeineCacheUtil.get(BANNERS);
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return bannerRepository.getBanners(pageRequest)
                                .stream()
                                .map(banner -> modelMapper.map(banner, BannerResponse.class))
                                .toList();
    }


}
