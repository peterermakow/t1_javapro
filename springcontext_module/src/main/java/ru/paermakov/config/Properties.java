package ru.paermakov.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("application.properties")
public class Properties {

    @Value("${database.url}")
    private String jdbcUrl;

    @Value("${database.user}")
    private String username;

    @Value("${database.password}")
    private String password;

}
