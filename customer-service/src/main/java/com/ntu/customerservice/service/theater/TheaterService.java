package com.ntu.customerservice.service.theater;

import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.moviecore.domain.theater.dto.ProvinceTheaterResponse;
import com.ntu.moviecore.domain.theater.dto.TheaterDb;
import com.ntu.moviecore.domain.theater.dto.TheaterResponse;
import com.ntu.moviecore.domain.theater.repository.TheaterRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ntu.common.constant.CacheAttributeKeyConstant.PROVINCE_THEATER;

@Service
@AllArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    private final ModelMapper modelMapper;

    public List<ProvinceTheaterResponse> getTheaters() {
        if (CaffeineCacheUtil.containsKey(PROVINCE_THEATER)) {
            return (List<ProvinceTheaterResponse>) CaffeineCacheUtil.get(PROVINCE_THEATER);
        }

        List<TheaterDb> theaters = theaterRepository.getTheaters();

        Map<String, ProvinceTheaterResponse> provinceMap = new HashMap<>();
        for (TheaterDb theaterDb : theaters) {
            ProvinceTheaterResponse province = provinceMap.get(theaterDb.getProvinceCode());
            if (province == null) {
                province = new ProvinceTheaterResponse();
                province.setProvinceCode(theaterDb.getProvinceCode());
                province.setProvinceName(theaterDb.getProvinceName());

                List<TheaterResponse> theatersResponse = new ArrayList<>();
                TheaterResponse theater = modelMapper.map(theaterDb, TheaterResponse.class);
                theatersResponse.add(theater);
                province.setTheaters(theatersResponse);

                provinceMap.put(theaterDb.getProvinceCode(), province);
            } else {
                TheaterResponse theater = modelMapper.map(theaterDb, TheaterResponse.class);
                province.getTheaters().add(theater);
            }
        }

        return provinceMap.values().stream().toList();
    }


}
