package com.jbrigido.library.mapper;

import com.jbrigido.library.dto.UserRequestDTO;
import com.jbrigido.library.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        return user;
    }

}
