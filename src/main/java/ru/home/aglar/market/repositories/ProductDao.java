package ru.home.aglar.market.repositories;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.utils.SessionFactoryUtils;

import java.util.List;

@Component("ProductDaoComponent")
public class ProductDao implements ProductRepository {
    private final SessionFactoryUtils factory;

    public ProductDao(SessionFactoryUtils factory) {
        this.factory = factory;
        this.factory.init();
    }

    @Override
    public Product findProductById(Long id) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public List<Product> findAllProducts() {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery(
                    "SELECT product FROM Product product ORDER BY product.id").getResultList();
            session.getTransaction().commit();
            return products;
        }

    }

    @Override
    public boolean deleteProductById(Long id) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
//            Product product = session.get(Product.class, id);
//            session.delete(product);
        int result = session.createQuery("DELETE FROM Product product WHERE product.id = :id")
                .setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        return result > 0;
        }
    }

    @Override
    public void saveOrUpdate(Product product) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
    }
}