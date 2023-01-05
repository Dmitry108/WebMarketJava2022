package ru.home.aglar.market.repositories;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.utils.SessionFactoryUtils;

import java.util.List;

@Component("ProductDaoComponent")
public class ProductDaoImpl implements ProductDAO {
    private SessionFactoryUtils factory;

    @Autowired
    public void init(SessionFactoryUtils factory) {
        System.out.println(factory);
        this.factory = factory;
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

    @Override
    public List<Customer> findCustomersOfProduct(Long id) {
        try (Session session = factory.getSession()){
            session.beginTransaction();
            List<Customer> customers = session.get(Product.class, id).getCustomers();
            customers.size();
            session.getTransaction().commit();
            return customers;
        }
    }
}