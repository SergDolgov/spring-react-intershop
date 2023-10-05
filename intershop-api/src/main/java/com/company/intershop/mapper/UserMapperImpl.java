package com.company.intershop.mapper;

import com.company.intershop.model.User;
import com.company.intershop.rest.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
}
