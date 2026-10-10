package com.ntu.adminservice.application.theater.service;

import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.persistence.material.entity.WardJpaEntity;
import com.ntu.persistence.material.repository.WardJpaRepository;
import com.ntu.moviecore.application.theater.dto.TheaterAdminResponse;
import com.ntu.moviecore.domain.theater.readmodel.TheaterSummary;
import com.ntu.moviecore.application.theater.dto.TheaterNewRequest;
import com.ntu.persistence.theater.entity.TheaterJpaEntity;
import com.ntu.persistence.theater.repository.TheaterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TheaterAdminService {

    private static final String THEATER = "Theater";

    private final WardRepository wardRepository;

    private final TheaterRepository theaterRepository;

    @Transactional(readOnly = true)
    public List<TheaterAdminResponse> getTheaters() {
        return theaterRepository.getTheaters()
                .stream()
                .map(TheaterAdminService::convertTheater)
                .toList();
    }

    /**
     * @param keyword      part of the theater name or address (case-insensitive), blank for every theater
     * @param provinceCode province to filter on, blank for every province
     * @param wardId       ward to filter on, {@code null} for every ward
     */
    @Transactional(readOnly = true)
    public List<TheaterAdminResponse> searchTheaters(String keyword, String provinceCode, Long wardId) {
        String normalizedKeyword = StringUtils.isBlank(keyword) ? null : keyword.trim().toLowerCase(Locale.ROOT);
        return getTheaters()
                .stream()
                .filter(t -> normalizedKeyword == null
                        || containsIgnoreCase(t.getName(), normalizedKeyword)
                        || containsIgnoreCase(t.getAddress(), normalizedKeyword))
                .filter(t -> StringUtils.isBlank(provinceCode) || provinceCode.equals(t.getProvinceCode()))
                .filter(t -> wardId == null || wardId.equals(t.getWardId()))
                .toList();
    }

    /**
     * @throws ResourceNotFoundException when the theater does not exist
     */
    @Transactional(readOnly = true)
    public TheaterAdminResponse getTheaterById(long theaterId) {
        return theaterRepository.getTheaterById(theaterId)
                .map(TheaterAdminService::convertTheater)
                .orElseThrow(() -> new ResourceNotFoundException(THEATER, theaterId));
    }

    @Transactional
    public void addNewTheater(TheaterNewRequest request) {
        Theater theater = new Theater();
        applyRequest(theater, request);
        Theater saved = theaterRepository.save(theater);
        keepSingleDefault(saved);
    }

    /**
     * @throws ResourceNotFoundException when the theater does not exist
     */
    @Transactional
    public void updateTheater(long theaterId, TheaterNewRequest request) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException(THEATER, theaterId));
        applyRequest(theater, request);
        Theater saved = theaterRepository.save(theater);
        keepSingleDefault(saved);
    }

    private void applyRequest(Theater theater, TheaterNewRequest request) {
        Ward ward = wardRepository.findById(request.getWardId())
                .orElseThrow(() -> new ResourceNotFoundException("Ward", request.getWardId()));
        theater.setName(request.getName().trim());
        theater.setAddress(request.getAddress().trim());
        theater.setHotline(request.getHotline().trim());
        theater.setWard(ward);
        theater.setDefault(request.isDefaultFlag());
    }

    /**
     * Only one theater can be the default one: when the saved theater is the default,
     * the flag is removed from every other theater.
     */
    private void keepSingleDefault(Theater theater) {
        if (theater.isDefault()) {
            theaterRepository.clearDefaultExcept(theater.getId());
        }
    }

    private static boolean containsIgnoreCase(String value, String lowerCaseKeyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(lowerCaseKeyword);
    }

    private static TheaterAdminResponse convertTheater(TheaterDb theaterDb) {
        TheaterAdminResponse response = new TheaterAdminResponse();
        response.setTheaterId(theaterDb.getTheaterId());
        response.setProvinceName(theaterDb.getProvinceName());
        response.setProvinceCode(theaterDb.getProvinceCode());
        response.setWardName(theaterDb.getWardName());
        response.setWardId(theaterDb.getWardId());
        response.setName(theaterDb.getTheaterName());
        response.setAddress(theaterDb.getTheaterAddress());
        response.setHotline(theaterDb.getTheaterHotline());
        response.setDefaultFlag(theaterDb.getIsDefault());
        return response;
    }
}
