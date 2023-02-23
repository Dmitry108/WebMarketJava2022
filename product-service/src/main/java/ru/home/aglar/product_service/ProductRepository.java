package ru.home.aglar.product_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByPriceBetween(Integer min, Integer max);

    @Query("SELECT product FROM Product product WHERE product.price >= ?1")
    List<Product> findAllByPriceHigh(Integer limit);

    @Query("SELECT product FROM Product product WHERE product.price <= ?1")
    List<Product> findAllByPriceLow(Integer limit);
}