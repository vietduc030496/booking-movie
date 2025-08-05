package com.ntu.adminservice.service.material;

import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.moviecore.domain.material.dto.WardResponse;
import com.ntu.moviecore.domain.material.entity.Ward;
import com.ntu.moviecore.domain.material.repository.ProvinceRepository;
import com.ntu.moviecore.domain.theater.dto.ProvinceResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ntu.common.constant.CacheAttributeKeyConstant.PROVINCES;

@Service
@AllArgsConstructor
public class ProvinceAdminService {

    private final ProvinceRepository provinceRepository;

    private final ModelMapper modelMapper;

    public List<ProvinceResponse> getAllProvince() {
        if (CaffeineCacheUtil.containsKey(PROVINCES)) {
            return (List<ProvinceResponse>) CaffeineCacheUtil.get(PROVINCES);
        }

        List<ProvinceResponse> provinces = new ArrayList<>();
        provinceRepository.findAll().forEach(p -> {
            ProvinceResponse province = modelMapper.map(p, ProvinceResponse.class);
            List<WardResponse> wards = new ArrayList<>();
            for (Ward w : p.getWards()) {
                WardResponse ward = modelMapper.map(w, WardResponse.class);
                ward.setProvinceCode(w.getProvince().getProvinceCode());
                wards.add(ward);
            }
            province.setWards(wards);
            provinces.add(province);
        });
        return provinces;
    }
}
