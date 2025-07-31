package com.ntu.site.api.momo;

import com.ntu.customerservice.service.momo.MomoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/momo")
@AllArgsConstructor
public class MomoController {

    private final MomoService momoService;

    @GetMapping
    @ResponseBody
    public String getMomoPage() throws Exception {
        momoService.MomoPaymentQr(1000,"package", "QR");
        return "success";
    }

    @GetMapping("/momo-callback")
    @ResponseBody
    public String getMomoQRPage() throws Exception {
        System.out.println("momo callback");
        return "momo-callback";
    }

    @GetMapping("/momo-callback/backend")
    @ResponseBody
    public String getMomoBackend() throws Exception {
        System.out.println("backend momo callback");
        return "momo-callback-backend";
    }
}
