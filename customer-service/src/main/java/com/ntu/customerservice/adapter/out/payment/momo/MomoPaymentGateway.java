package com.ntu.customerservice.adapter.out.payment.momo;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MomoService {

    private final MomoProperties momoProperties;

    public PaymentResponse momoPaymentQr(long amount, String packageName, String paymentMethod) throws Exception {

        String requestId = String.valueOf(System.currentTimeMillis() + "learningz");
        String orderId = String.valueOf(System.currentTimeMillis() + "learningz");
        log.debug("Sent orderId: {}", orderId);
        String orderInfo = "LearningZ payment " + packageName + " " + paymentMethod;
        String returnURL = momoProperties.getReturnUrl();
        String notifyURL = momoProperties.getNotifyURL();
        Environment environment = Environment.selectEnv(momoProperties.getEnvironment());
        log.debug("Debug: orderId={}, requestId={}", orderId, requestId);

        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
        log.debug("QR: {}", captureWalletMoMoResponse.getQrCodeUrl());
        log.debug("Pay URL: {}", captureWalletMoMoResponse.getPayUrl());

        return captureWalletMoMoResponse;
    }

}
