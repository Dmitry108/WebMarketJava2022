package ru.home.aglar.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.aglar.market.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
