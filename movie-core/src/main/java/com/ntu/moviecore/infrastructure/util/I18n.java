package com.ntu.moviecore.infrastructure.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class I18n {
    private I18n() {
    }

    private static MessageSource messageSource;
    private static Locale defaultLocale;

    /**
     * @param inject MessageSource
     * @see com.ntu.infrastructure.config.ApplicationConfig
     */
    public static void init(MessageSource inject) {
        I18n.messageSource = inject;
        defaultLocale = Locale.KOREAN;
    }

    public static String getMessageEn(String code) {
        return messageSource.getMessage(code, null, StringUtils.EMPTY, Locale.ENGLISH);
    }

    public static String getMessageEn(String code, Object... params) {
        return messageSource.getMessage(code, params, StringUtils.EMPTY, Locale.ENGLISH);
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, StringUtils.EMPTY, defaultLocale);
    }

    public static String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, StringUtils.EMPTY, locale);
    }

    public static String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, StringUtils.EMPTY, defaultLocale);
    }
}
