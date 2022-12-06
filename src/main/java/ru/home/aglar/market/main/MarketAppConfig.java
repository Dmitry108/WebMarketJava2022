package ru.home.aglar.market.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.home.aglar.market.di.config.AppConfig;
import ru.home.aglar.market.di.config.Cart;
import ru.home.aglar.market.di.config.ProductRepository;

public class MarketAppConfig {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductRepository repo = context.getBean(ProductRepository.class);
        System.out.println("Список товаров:");
        repo.getAllProducts().forEach(System.out::println);

        System.out.println("В корзину №1 добавлены id 1, 2, 5");
        Cart cart1 = context.getBean(Cart.class);
        cart1.addProduct(1);
        cart1.addProduct(2);
        cart1.addProduct(5);
        System.out.println("В корзину №2 добавлены id 4, 3, 2");
        Cart cart2 = context.getBean(Cart.class);
        cart2.addProduct(4);
        cart2.addProduct(3);
        cart2.addProduct(2);
        System.out.printf("Корзина №1: %s%nКорзина №2: %s%n", cart1.getCartList(), cart2.getCartList());
        System.out.println("Из корзины №1 удален id 1, 6(несуществующий)");
        cart1.removeProduct(1);
        cart1.removeProduct(6);
        System.out.println("Из корзины №2 удалены id 4, 2");
        cart2.removeProduct(4);
        cart2.removeProduct(2);
        System.out.printf("Корзина №1: %s%nКорзина №2: %s%n", cart1.getCartList(), cart2.getCartList());
    }
}