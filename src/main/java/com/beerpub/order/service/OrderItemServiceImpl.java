package com.beerpub.order.service;

import com.beerpub.order.converter.OrderItemConverter;
import com.beerpub.order.dao.*;
import com.beerpub.order.dto.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    // get order items by order id
    @Override
    public List<OrderItemDTO> getOrderByUserId(Integer orderId) {
        List<OrderItem> orderList = orderItemRepository.findByOrderId(orderId);
        return orderList.stream().map(OrderItemConverter::convertOrderItem).collect(Collectors.toList());
    }

    // add order items by order id or add quantity
    @Override
    public OrderItemDTO addOrderItem(Integer orderId, Integer itemId){

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item doesn't exist!"));

        if (item.getAvailability() == false) { throw new RuntimeException("Item is not available!");}

        orderRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order doesn't exist!"));

        // find if this item already exist in this order
        Optional<OrderItem> existingOrderItem = orderItemRepository.findByOrderIdAndItemId(orderId, item.getItemId());

        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + 1);
            orderItemRepository.save(orderItem);
            return OrderItemConverter.convertOrderItem(orderItem);
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setItemId(item.getItemId());
        orderItem.setPrice(item.getPrice());
        orderItem.setQuantity(1);

        orderItemRepository.save(orderItem);
        return OrderItemConverter.convertOrderItem(orderItem);
    }

    // delete
    @Override
    public void deleteOrderItem(Integer orderItemId) {
        orderItemRepository.findById(orderItemId).orElseThrow(() -> new IllegalArgumentException("Item doesn't exist!"));
        orderItemRepository.deleteById(orderItemId);
    }

    // setQuantity - 1
    @Override
    public OrderItemDTO decreaseOrderItemQuantity(Integer orderItemId) {
        // find order item exist
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItemId);

        if (optionalOrderItem.isPresent()) {
            OrderItem orderItem = optionalOrderItem.get();

            if (orderItem.getQuantity() > 1) {
                orderItem.setQuantity(orderItem.getQuantity() - 1);
                orderItemRepository.save(orderItem);
                return OrderItemConverter.convertOrderItem(orderItem);
            } else {
                orderItemRepository.delete(orderItem);
                throw new IllegalArgumentException("Item quantity cannot be less than 1, item has been removed from the order.");
            }
        } else {
            // 订单项不存在，抛出异常
            throw new IllegalArgumentException("OrderItem not found for the given orderId and itemId.");
        }
    }





}
