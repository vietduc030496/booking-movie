package com.ntu.infrastructure.constant;

public class UrlConstant {
    private UrlConstant() {}

    public static final String API_VERSION = "/api/v1";

    public static final String CAPTCHA_URL = API_VERSION + "/captcha";

    public static final String VIEWS_URL = "/views";

    public static final String ACCOUNT_VIEW_URL = VIEWS_URL + "/account";

    public static final String ERROR_URL = VIEWS_URL + "/error";


}
