package com.beerpub.order.converter;

import com.beerpub.order.dao.User;
import com.beerpub.order.dto.UserDTO;

public class UserConverter {

    public static UserDTO convertUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static User convertUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setRole(userDTO.getRole());
        return user;
    }
}
