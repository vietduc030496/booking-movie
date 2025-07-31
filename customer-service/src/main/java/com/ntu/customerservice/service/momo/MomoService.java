package com.ntu.customerservice.service.momo;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.utils.Encoder;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Formatter;

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
