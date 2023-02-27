package ru.home.aglar.market.core.repositories;

import ru.home.aglar.market.core.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.username = ?1")
    List<Order> findAllByUsername(String username);
}