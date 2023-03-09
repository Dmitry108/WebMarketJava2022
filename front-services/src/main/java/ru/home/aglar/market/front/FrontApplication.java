package ru.home.aglar.market.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FrontApplication {
    @Bean
    public SimpleMessageReceiver simpleMessageReceiver() {
        return new SimpleMessageReceiver();
    }
    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }
}