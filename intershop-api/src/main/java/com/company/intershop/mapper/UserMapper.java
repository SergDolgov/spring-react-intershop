package com.company.intershop.mapper;

import com.company.intershop.domain.User;
import com.company.intershop.rest.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}