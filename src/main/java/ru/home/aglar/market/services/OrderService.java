package ru.home.aglar.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Order;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.OrderDAO;

import java.util.List;

@Service
public class OrderService {
    private OrderDAO orderDAO;

    @Autowired
    public void init(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<Order> getOrders() {
        return orderDAO.getAllOrders();
    }

    public void addOrder(Customer customer, Product product) {
        orderDAO.addOrder(customer, product);
    }
    public List<Product> getProductsOfCustomer(Customer customer) {
        return orderDAO.findProductsOfCustomer(customer);
    }

    public List<Customer> getCustomersOfProduct(Product product) {
        return orderDAO.findCustomersOfProduct(product);
    }
}
