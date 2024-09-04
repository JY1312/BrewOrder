package com.beerpub.order.service;

import com.beerpub.order.dao.User;

public interface UserService {
    public boolean authenticate(String username, String password);
}
