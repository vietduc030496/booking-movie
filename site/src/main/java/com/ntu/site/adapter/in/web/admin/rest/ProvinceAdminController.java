package com.ntu.site.adapter.in.web.admin.rest;

import com.ntu.adminservice.application.material.service.WardAdminService;
import com.ntu.moviecore.application.material.dto.WardResponse;
import com.ntu.site.adapter.in.web.response.CollectionDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ntu.common.constant.UrlConstant.PROVINCE_ADMIN_URL;

@RestController
@RequestMapping(PROVINCE_ADMIN_URL)
@AllArgsConstructor
public class ProvinceAdminController {

    private final WardAdminService wardAdminService;

    @GetMapping("/{provinceId}/wards")
    public ResponseEntity<CollectionDataResponse<WardResponse>> getAllWardsInOneProvince(@PathVariable("provinceId") Long provinceId) {
        List<WardResponse> wards = wardAdminService.findAllWardsByProvinceId(provinceId);
        return ResponseEntity.ok(CollectionDataResponse.success(wards));
    }
}
