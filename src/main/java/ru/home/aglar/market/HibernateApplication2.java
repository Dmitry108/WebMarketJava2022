package ru.home.aglar.market;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.home.aglar.market.entities.Customer;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.CustomerDAOImpl;
import ru.home.aglar.market.repositories.OrderDAOImpl;
import ru.home.aglar.market.repositories.ProductDaoImpl;
import ru.home.aglar.market.services.CustomerService;
import ru.home.aglar.market.services.OrderService;
import ru.home.aglar.market.services.ProductService;
import ru.home.aglar.market.utils.SessionFactoryUtils;

import java.util.List;

public class HibernateApplication2 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.home.aglar.market");
        context.getBean(SessionFactoryUtils.class);
        context.getBean(ProductDaoImpl.class);
        context.getBean(CustomerDAOImpl.class);
        context.getBean(OrderDAOImpl.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        ProductService productService = context.getBean(ProductService.class);
        OrderService orderService = context.getBean(OrderService.class);



        List<Customer> customers = customerService.getCustomers();
        customers.forEach(customer -> {
            System.out.println(customer.getName() + ":");
            orderService.getProductsOfCustomer(customer).forEach(System.out::println);
        });

        List<Product> products = productService.getProducts();
        products.forEach(product -> {
            System.out.println(product.getTitle() + ":");
            orderService.getCustomersOfProduct(product).forEach(System.out::println);
        });
        productService.changeProductCost(5L, 999);
        orderService.addOrder(customerService.getCustomerById(3L), productService.getProductById(5L));
        productService.changeProductCost(5L, -222);
        orderService.addOrder(customerService.getCustomerById(3L), productService.getProductById(5L));
        System.out.println(orderService.getOrders());
    }
}