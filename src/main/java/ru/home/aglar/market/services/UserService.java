package ru.home.aglar.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.User;
import ru.home.aglar.market.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
