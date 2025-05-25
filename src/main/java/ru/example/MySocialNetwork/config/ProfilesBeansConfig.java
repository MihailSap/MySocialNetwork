package ru.example.MySocialNetwork.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfilesBeansConfig {

    @Bean
    @Profile(value = "dev")
    public String logDevProfile(){
        log.info("Ваш текущий профиль: dev");
        return "dev";
    }

    @Bean
    @Profile(value = "test")
    public String logTestProfile(){
        log.info("Ваш текущий профиль: test");
        return "test";
    }

    @Bean
    @Profile(value = "prod")
    public String logProdProfile(){
        log.info("Ваш текущий профиль: prod");
        return "prod";
    }
}
