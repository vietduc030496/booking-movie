package com.ntu.customerservice.service.theater;

import com.ntu.moviecore.domain.theater.dto.ProvinceResponse;
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

@Service
@AllArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    private final ModelMapper modelMapper;

    public List<ProvinceResponse> getTheaters() {
        List<TheaterDb> theaters = theaterRepository.getTheaters();

        Map<String, ProvinceResponse> provinceMap = new HashMap<>();
        for (TheaterDb theaterDb : theaters) {
            ProvinceResponse province = provinceMap.get(theaterDb.getProvinceCode());
            if (province == null) {
                province = new ProvinceResponse();
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
