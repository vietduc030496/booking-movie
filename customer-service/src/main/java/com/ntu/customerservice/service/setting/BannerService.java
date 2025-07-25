package com.ntu.customerservice.service.setting;

import com.ntu.moviecore.domain.setting.dto.BannerResponse;
import com.ntu.moviecore.domain.setting.repository.BannerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final ModelMapper modelMapper;

    public List<BannerResponse> getBanners(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return bannerRepository.getBanners(pageRequest)
                                .stream()
                                .map(banner -> modelMapper.map(banner, BannerResponse.class))
                                .toList();
    }


}
