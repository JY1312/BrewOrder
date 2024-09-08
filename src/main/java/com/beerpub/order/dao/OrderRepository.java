package com.beerpub.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    //@Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.status = :status")
    Optional<Order> findByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") String status);

    List<Order> findByUserId(Integer userId);

    Optional<Order> findByOrderId(Integer orderId);
}
