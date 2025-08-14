package com.ntu.adminservice.service.theater;

import com.ntu.moviecore.domain.material.entity.Ward;
import com.ntu.moviecore.domain.material.repository.WardRepository;
import com.ntu.moviecore.domain.theater.dto.TheaterAdminResponse;
import com.ntu.moviecore.domain.theater.dto.TheaterDb;
import com.ntu.moviecore.domain.theater.dto.TheaterNewRequest;
import com.ntu.moviecore.domain.theater.entity.Theater;
import com.ntu.moviecore.domain.theater.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TheaterAdminService {

    private WardRepository wardRepository;

    private TheaterRepository theaterRepository;

    public List<TheaterAdminResponse> getTheaters() {
        List<TheaterDb> theaters = theaterRepository.getTheaters();
        return theaters.stream()
                .map(this::convertTheater)
                .toList();
    }

    public TheaterAdminResponse getTheaterById(Long theaterId) {
        Optional<TheaterDb> optional = theaterRepository.getTheaterById(theaterId);
        if (optional.isEmpty()) {
            return null;
        }
        TheaterDb theaterDb = optional.get();
        return convertTheater(theaterDb);
    }

    private TheaterAdminResponse convertTheater(TheaterDb theaterDb) {
        TheaterAdminResponse response = new TheaterAdminResponse();
        response.setTheaterId(theaterDb.getTheaterId());
        response.setProvinceName(theaterDb.getProvinceName());
        response.setProvinceCode(theaterDb.getProvinceCode());
        response.setWardName(theaterDb.getWardName());
        response.setWardId(theaterDb.getWardId());
        response.setDefaultFlag(theaterDb.getIsDefault());
        response.setName(theaterDb.getTheaterName());
        response.setAddress(theaterDb.getTheaterAddress());
        response.setHotline(theaterDb.getTheaterHotline());
        response.setDefaultFlag(theaterDb.getIsDefault());
        return response;
    }

    @Transactional
    public void addNewTheater(TheaterNewRequest newTheater) {
        Ward ward = wardRepository.findById(newTheater.getWardId()).get();
        Theater theater = new Theater();
        theater.setAddress(newTheater.getAddress());
        theater.setWard(ward);
        theaterRepository.save(theater);
    }

    @Transactional
    public void updateTheater(Long theaterId, TheaterNewRequest theaterNewRequest) {
        Optional<Theater> optional = theaterRepository.findById(theaterId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Theater not found");
        }
        // TODO: remove theater old default theater if exist

        Theater theater = optional.get();
        theater.setAddress(theaterNewRequest.getAddress());
        theater.setWard(wardRepository.findById(theaterNewRequest.getWardId()).get());
        theater.setHotline(theaterNewRequest.getHotline());
        theater.setDefault(theater.isDefault());

        theaterRepository.save(theater);
    }
}
