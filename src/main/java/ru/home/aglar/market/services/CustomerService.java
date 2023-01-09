package ru.home.aglar.market.services;

import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.repositories.CustomerDAO;

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

    public Customer getCustomerById(Long id) {
        return customerDAO.findCustomerById(id);
    }

    public void deleteCustomer(Long id) {customerDAO.deleteCustomerById(id);
    }
}