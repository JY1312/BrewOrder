package com.beerpub.order.service;

import com.beerpub.order.dao.Order;
import com.beerpub.order.dao.OrderRepository;
import com.beerpub.order.dao.User;
import com.beerpub.order.dao.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrderByUserId(int userId){
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return null;
    }
}
