package com.beerpub.order.service;

import com.beerpub.order.dao.User;
import com.beerpub.order.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        User user = userRepository.getByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public boolean signup(String username, String password, String phoneNumber, String role) {
        if (userRepository.getByUsername(username) != null){
            return false;
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setRole(role);

        userRepository.save(newUser);
        return true;
    }
}
