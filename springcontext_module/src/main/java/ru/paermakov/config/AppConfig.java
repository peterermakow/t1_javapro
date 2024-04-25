package ru.paermakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.paermakov.dao.UserDAO;
import ru.paermakov.service.UserService;

@Configuration
public class AppConfig {

    @Bean
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Bean(name = "userService")
    public UserService getUserService(UserDAO userDAO) {
        return new UserService(userDAO);
    }
}
