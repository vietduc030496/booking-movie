package com.ntu.customerservice.service.momo;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MomoService {

    private final MomoProperties momoProperties;

    public PaymentResponse MomoPaymentQr(long amount, String packageName, String paymentMethod) throws Exception {

        String requestId = String.valueOf(System.currentTimeMillis() + "learningz");
        String orderId = String.valueOf(System.currentTimeMillis() + "learningz");
        System.out.println("Sent orderId: " + orderId);
        String orderInfo = "LearningZ payment " + packageName + " " + paymentMethod;
        String returnURL = momoProperties.getReturnUrl();
        String notifyURL = momoProperties.getNotifyURL();
        Environment environment = Environment.selectEnv(momoProperties.getEnvironment());
        System.out.println("Debug: orderId=" + orderId + ", requestId=" + requestId);

        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
        System.out.println("QR: " + captureWalletMoMoResponse.getQrCodeUrl());
        System.out.println("Pay URL: " + captureWalletMoMoResponse.getPayUrl());

        return captureWalletMoMoResponse;
    }

}
