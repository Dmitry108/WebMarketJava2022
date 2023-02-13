package ru.home.aglar.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.aglar.market.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
