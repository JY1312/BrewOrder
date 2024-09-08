package com.beerpub.order.service;

import com.beerpub.order.dao.Order;
import com.beerpub.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    public OrderDTO getCart(Integer userId);

    public List<OrderDTO> getOrderByUserId(Integer userId);

    public OrderDTO confirmOrder(Integer orderId);

    public void deleteOrderById(Integer orderId);
}
