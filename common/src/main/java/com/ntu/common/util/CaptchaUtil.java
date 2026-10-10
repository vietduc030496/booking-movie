package com.ntu.common.util;

import java.security.SecureRandom;

public class CaptchaUtil {

    private CaptchaUtil() {
    }

    public static final String CAPTCHA_KEY = "captcha";

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateCaptcha() {
        int length = 5;
        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = RANDOM.nextInt(10);
            captcha.append(digit);
        }
        return captcha.toString();
    }
}
