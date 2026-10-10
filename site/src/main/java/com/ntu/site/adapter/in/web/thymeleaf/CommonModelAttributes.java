package com.ntu.site.adapter.in.web.thymeleaf;

import com.ntu.customerservice.application.theater.service.TheaterService;
import com.ntu.moviecore.application.theater.dto.ProvinceTheaterResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

import static com.ntu.common.constant.CacheAttributeKeyConstant.PROVINCE_THEATER;

@ControllerAdvice
@AllArgsConstructor
public class CommonModelAttributes {

    private final TheaterService theaterService;

    @ModelAttribute(PROVINCE_THEATER)
    public List<ProvinceTheaterResponse> branchList() {
        return theaterService.getTheaters();
    }
}
