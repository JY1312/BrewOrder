package com.beerpub.order.service;

import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {

    public List<OrderItemDTO> getOrderByUserId(Integer orderId);

    public void deleteOrderItem(Integer orderItemId);

    public OrderItemDTO decreaseOrderItemQuantity(Integer orderItemId);

    public OrderItemDTO addOrderItem(Integer orderId, Integer itemId);
}
