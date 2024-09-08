package com.beerpub.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderId(Integer orderId);

    Optional<OrderItem> findByOrderIdAndItemId(Integer orderId, Integer itemId);
}
