package com.beerpub.order.converter;

import com.beerpub.order.dao.OrderItem;
import com.beerpub.order.dto.OrderItemDTO;

public class OrderItemConverter {

    public static OrderItemDTO convertOrderItem (OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setItemId(orderItem.getItemId());
        orderItemDTO.setOrderId(orderItem.getOrderId());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setQuantity(orderItemDTO.getQuantity());
        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
        return orderItemDTO;
    }

    public static OrderItem convertOrderItemDTO (OrderItemDTO orderItemDTO ) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(orderItemDTO.getItemId());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrderId(orderItemDTO.getOrderId());
        return orderItem;
    }
}
