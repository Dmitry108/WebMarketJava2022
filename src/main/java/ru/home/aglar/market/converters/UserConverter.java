package ru.home.aglar.market.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.UserDto;
import ru.home.aglar.market.entities.User;

@Component
public class UserConverter {
    public User convertFromDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getUsername(), null, userDto.getEmail(),
                userDto.getRoles(), userDto.getCreatedAt(), userDto.getUpdatedAt());
    }

    public UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getRoles(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
