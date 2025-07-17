package com.ntu.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.ntu.common",
                                "com.ntu.moviecore",
                                "com.ntu.customerservice",
                                "com.ntu.site"})
@EnableJpaRepositories(basePackages = "com.ntu.moviecore.domain")
@EntityScan(basePackages = "com.ntu.moviecore.domain")
@SpringBootApplication
public class SiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

}
