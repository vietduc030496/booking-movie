package com.ntu.common.constant;

public class UrlConstant {
    private UrlConstant() {
    }

    public static final String API_VERSION = "/api/v1";

    public static final String CAPTCHA_URL = API_VERSION + "/captcha";

    public static final String VIEWS_URL = "/views";

    public static final String ACCOUNT_VIEW_URL = VIEWS_URL + "/account";

    public static final String ERROR_URL = VIEWS_URL + "/error";

    public static final String MOVIE_VIEW_URL = VIEWS_URL + "/movies";

    public static final String SHOWTIME_VIEW_URL = VIEWS_URL + "/showtimes";

    public static final String ADMIN_VIEW_URL = VIEWS_URL + "/admin";

    public static final String ADMIN_MOVIE_VIEW_URL = ADMIN_VIEW_URL + "/movies";
}
