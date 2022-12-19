package ru.home.aglar.market;

import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.ProductDao;
import ru.home.aglar.market.utils.SessionFactoryUtils;

public class HibernateApplication {
    public static void main(String[] args) {
        ProductDao repository = new ProductDao(new SessionFactoryUtils());
        System.out.println(repository.findProductById(1L));
        System.out.println(repository.findAllProducts());
        Product product = new Product();
        product.setTitle("Эхолёт");
        product.setPrice(2500);
        repository.saveOrUpdate(product);
        repository.deleteProductById(1L);
        Product product1 = repository.findProductById(2L);
        product1.setPrice(5557);
        repository.saveOrUpdate(product1);
        System.out.println(repository.findAllProducts());
    }
}