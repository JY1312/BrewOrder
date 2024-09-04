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
    public boolean authenticate(String username, String password) {
        User user = userRepository.getByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
