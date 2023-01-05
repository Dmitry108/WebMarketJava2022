package ru.home.aglar.market;

import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.CustomerDAOImpl;
import ru.home.aglar.market.repositories.ProductDaoImpl;
import ru.home.aglar.market.utils.SessionFactoryUtils;

import java.util.List;

public class HibernateApplication2 {
    public static void main(String[] args) {
        SessionFactoryUtils factory = new SessionFactoryUtils();
        factory.init();
        ProductDaoImpl productDAO = new ProductDaoImpl();
        productDAO.init(factory);
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        customerDAO.init(factory);
        List<Customer> customers = customerDAO.findAllCustomers();
        customers.forEach(customer -> {
            System.out.println(customer.getName() + ":");
            customerDAO.findProductsOfCustomer(customer.getId()).forEach(System.out::println);
        });
        List<Product> products = productDAO.findAllProducts();
        products.forEach(product -> {
            System.out.println(product.getTitle() + ":");
            productDAO.findCustomersOfProduct(product.getId()).forEach(System.out::println);
        });
    }
}