package com.beerpub.order.converter;

import com.beerpub.order.dao.Order;
import com.beerpub.order.dto.OrderDTO;

import java.util.function.Function;

public class OrderConverter {

    public static OrderDTO convertOrder(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUserId(order.getOrderId());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderId(order.getOrderId());
        return orderDTO;
    }

    public static Order convertOrderDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        order.setUserId(orderDTO.getUserId());
        order.setStatus(orderDTO.getStatus());
        order.setTotalAmount(orderDTO.getTotalAmount());
        return order;
    }
}
