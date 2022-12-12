package ru.home.aglar.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.home.aglar.market.model.Product;
import ru.home.aglar.market.repositories.ProductRepository;

@Controller
public class ProductsController {
    private ProductRepository repository;

    @Autowired
    public void setProductRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    private String getProducts(Model model) {
        model.addAttribute("products", repository.getAllProducts());
        return "products_page";
    }

    @GetMapping("/product_info")
    private String getProductInfo(Model model, @RequestParam(name = "product_id") long id) {
        model.addAttribute("product", repository.getProductById(id));
        return "product_info_page";
    }

    @GetMapping("/add_product_form")
    private String showAddProductForm() {
        return "add_product_page";
    }

    @GetMapping("/add_product")
    private String addProduct(@RequestParam String title, @RequestParam Integer cost) {
        long id = repository.generateId();
        if (repository.addProduct(new Product(id, title, cost)))
            return "redirect:/products";
        else
            return null;
    }
}