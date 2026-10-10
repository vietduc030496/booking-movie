package com.ntu.site.api.admin.view;

import com.ntu.common.config.MessageSourceConfig;
import com.ntu.site.api.admin.view.movie.MovieAdminViewController;
import com.ntu.site.api.admin.view.movie.ShowtimeAdminViewController;
import com.ntu.site.api.admin.view.theater.RoomAdminViewController;
import com.ntu.site.api.admin.view.theater.TheaterAdminViewController;
import com.ntu.site.application.exception.advice.GlobalViewControllerAdvice;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Web slice configuration of the admin pages: only the admin controllers and the Thymeleaf
 * views, without the database, Elasticsearch and Redis needed by {@code SiteApplication}.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({MessageSourceConfig.class,
        GlobalViewControllerAdvice.class,
        AdminViewController.class,
        MovieAdminViewController.class,
        ShowtimeAdminViewController.class,
        TheaterAdminViewController.class,
        RoomAdminViewController.class})
class AdminViewTestConfig {
}
