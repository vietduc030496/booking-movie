package com.ntu.site.api.momo;

import com.ntu.customerservice.service.momo.MomoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/momo")
@AllArgsConstructor
public class MomoController {

    private final MomoService momoService;

    @GetMapping
    public String getMomoPage() throws Exception {
        momoService.momoPaymentQr(1000,"package", "QR");
        return "success";
    }

    @GetMapping("/momo-callback")
    public String getMomoQRPage() {
        log.debug("momo callback");
        return "momo-callback";
    }

    @GetMapping("/momo-callback/backend")
    public String getMomoBackend() {
        log.debug("backend momo callback");
        return "momo-callback-backend";
    }
}
