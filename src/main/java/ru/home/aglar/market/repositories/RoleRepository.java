package ru.home.aglar.market.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.aglar.market.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
