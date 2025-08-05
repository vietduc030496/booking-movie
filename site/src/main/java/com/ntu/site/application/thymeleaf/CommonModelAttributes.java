package com.ntu.site.application.thymeleaf;

import com.ntu.customerservice.service.theater.TheaterService;
import com.ntu.moviecore.domain.theater.dto.ProvinceTheaterResponse;
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
