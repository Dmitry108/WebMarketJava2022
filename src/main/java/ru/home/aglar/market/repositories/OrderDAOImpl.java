package ru.home.aglar.market.repositories;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Order;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.utils.SessionFactoryUtils;

import java.util.List;

@Component
public class OrderDAOImpl implements OrderDAO {
    private SessionFactoryUtils factory;

    @Autowired
    public void init(SessionFactoryUtils factory) {
        this.factory = factory;
    }

    @Override
    public void addOrder(Customer customer, Product product) {
        try (Session session = factory.getSession()){
            session.beginTransaction();
            Order order = new Order(customer, product);
            session.save(order);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        try (Session session = factory.getSession()){
            session.beginTransaction();
            List<Order> orders = session.createQuery("SELECT order FROM Order order ORDER BY order.id")
                    .getResultList();
            session.getTransaction().commit();
            return orders;
        }
    }

    @Override
    public List<Product> findProductsOfCustomer(Customer customer) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery("SELECT order.product FROM Order order WHERE order.customer =: customer")
                    .setParameter("customer", customer).getResultList();
            products.size();
            session.getTransaction().commit();
            return products;
        }
    }

    @Override
    public List<Customer> findCustomersOfProduct(Product product) {
        try (Session session = factory.getSession()){
            session.beginTransaction();
            List<Customer> customers = session.createQuery("SELECT order.customer FROM Order order WHERE order.product =: product")
                    .setParameter("product", product)
                    .getResultList();
            customers.size();
            session.getTransaction().commit();
            return customers;
        }
    }
}