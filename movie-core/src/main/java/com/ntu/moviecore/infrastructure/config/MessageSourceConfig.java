package com.ntu.moviecore.infrastructure.config;

import com.ntu.moviecore.infrastructure.util.I18n;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:i18n/error",
                "classpath:i18n/messages",
                "classpath:i18n/validate");
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setDefaultEncoding("UTF-8");
        I18n.init(messageSource);
        return messageSource;
    }



}
