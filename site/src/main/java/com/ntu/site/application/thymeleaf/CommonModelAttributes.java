package com.ntu.site.application.thymeleaf;

import com.ntu.customerservice.service.theater.TheaterService;
import com.ntu.moviecore.domain.theater.dto.ProvinceResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class CommonModelAttributes {

    private final TheaterService theaterService;

    @ModelAttribute("provinces")
    public List<ProvinceResponse> branchList() {
        return theaterService.getTheaters();
    }
}
