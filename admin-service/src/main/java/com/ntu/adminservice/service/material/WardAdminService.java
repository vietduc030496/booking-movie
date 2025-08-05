package com.ntu.adminservice.service.material;

import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.moviecore.domain.material.dto.WardResponse;
import com.ntu.moviecore.domain.material.entity.Ward;
import com.ntu.moviecore.domain.material.repository.WardRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.ntu.common.constant.CacheAttributeKeyConstant.PROVINCE_WARDS_MAP;
import static com.ntu.common.constant.CacheAttributeKeyConstant.WARDS;

@Service
@AllArgsConstructor
public class WardAdminService {

    private final WardRepository wardRepository;

    private final ModelMapper modelMapper;

    public List<WardResponse> getAllWard() {
        if (CaffeineCacheUtil.containsKey(WARDS)) {
            return (List<WardResponse>) CaffeineCacheUtil.get(WARDS);
        }
        List<WardResponse> wards = new ArrayList<>();
        wardRepository.findAll().forEach(w -> {
            WardResponse ward = modelMapper.map(w, WardResponse.class);
            ward.setProvinceCode(w.getProvince().getProvinceCode());
            wards.add(ward);
        });
        return wards;
    }

    public List<WardResponse> findAllWardsByProvinceId(long provinceId) {
        if (CaffeineCacheUtil.containsKey(PROVINCE_WARDS_MAP)) {
            Map<Long, List<WardResponse>> provinceWardsMap = (Map<Long, List<WardResponse>>) CaffeineCacheUtil.get(PROVINCE_WARDS_MAP);
            return provinceWardsMap.get(provinceId);
        }

        List<WardResponse> wards = new ArrayList<>();
        wardRepository.findByProvinceId(provinceId).forEach(w -> {
            WardResponse ward = modelMapper.map(w, WardResponse.class);
            ward.setProvinceCode(w.getProvince().getProvinceCode());
            wards.add(ward);
        });
        return wards;
    }
}
