package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Product;
import java.util.List;

@Component
public interface CustomerDAO {
    Customer findCustomerById(Long id);
    List<Customer> findAllCustomers();
    boolean deleteCustomerById(Long id);
    void saveOrUpdate(Customer product);
    List<Product> findProductsOfCustomer(Long id);
}