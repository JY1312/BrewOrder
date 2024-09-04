package com.beerpub.order.service;

public interface UserService {
    public boolean login(String username, String password);

    public boolean signup(String username, String password, String phoneNumber, String role);
}
