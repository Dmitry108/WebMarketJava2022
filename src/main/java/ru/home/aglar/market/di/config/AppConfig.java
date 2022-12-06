package ru.home.aglar.market.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("ru.home.aglar.market.di.config")
public class AppConfig {
    @Bean
    public ProductRepository productRepository() {
        SimpleProductRepository repository = new SimpleProductRepository();
        repository.initRepo();
        return repository;
    }

    @Bean
    @Scope("prototype")
    public Cart cart() {
        Cart cart = new Cart();
        cart.initCart();
        cart.setRepository(productRepository());
        return cart;
    }
}