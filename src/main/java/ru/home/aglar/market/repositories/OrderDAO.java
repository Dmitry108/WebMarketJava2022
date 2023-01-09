package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Order;
import ru.home.aglar.market.entities.Product;

import java.util.List;

@Component
public interface OrderDAO {
    void addOrder(Customer customer, Product product);
    List<Order> getAllOrders();
    List<Product> findProductsOfCustomer(Customer customer);
    List<Customer> findCustomersOfProduct(Product product);
}
