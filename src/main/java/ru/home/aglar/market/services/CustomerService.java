package ru.home.aglar.market.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.CustomerDAO;
import ru.home.aglar.market.repositories.ProductDAO;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDao) {
        this.customerDAO = customerDao;
    }

    public List<Customer> getCustomers() {
        return customerDAO.findAllCustomers();
    }

    public void deleteCustomer(Long id) {customerDAO.deleteCustomerById(id);
    }
}