package com.ntu.adminservice.service.theater;

import com.ntu.moviecore.domain.material.entity.Ward;
import com.ntu.moviecore.domain.material.repository.WardRepository;
import com.ntu.moviecore.domain.theater.dto.TheaterDb;
import com.ntu.moviecore.domain.theater.dto.TheaterNewRequest;
import com.ntu.moviecore.domain.theater.dto.TheaterResponse;
import com.ntu.moviecore.domain.theater.entity.Theater;
import com.ntu.moviecore.domain.theater.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TheaterAdminService {
    private final ModelMapper modelMapper;

    private WardRepository wardRepository;

    private TheaterRepository theaterRepository;

    public List<TheaterResponse> getTheaters() {
        List<TheaterDb> theaters = theaterRepository.getTheaters();
        return theaters.stream()
                .map(theater -> modelMapper.map(theater, TheaterResponse.class))
                .toList();
    }

    @Transactional
    public void addNewTheater(TheaterNewRequest newTheater) {
        Ward ward = wardRepository.findById(newTheater.getWardId()).get();
        Theater theater = new Theater();
        theater.setAddress(newTheater.getAddress());
        theater.setWard(ward);
        theaterRepository.save(theater);
    }
}
