package ru.home.aglar.market.repositories;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.utils.SessionFactoryUtils;

import java.util.List;

@Component
public class CustomerDAOImpl implements CustomerDAO {

    private SessionFactoryUtils factory;

    @Autowired
    public void init(SessionFactoryUtils factory) {
        this.factory = factory;
    }

    @Override
    public Customer findCustomerById(Long id) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            session.getTransaction().commit();
            return customer;
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        System.out.println(factory.getSession());
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            List<Customer> customers = session.createQuery("SELECT c FROM Customer c ORDER BY id").getResultList();
            session.getTransaction().commit();
            return customers;
        }
    }

    @Override
    public boolean deleteCustomerById(Long id) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            int result = session.createQuery("DELETE FROM Customer c WHERE c.id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            return result > 0;
        }
    }

    @Override
    public void saveOrUpdate(Customer customer) {
        try (Session session = factory.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(customer);
            session.getTransaction().commit();
        }
    }
}