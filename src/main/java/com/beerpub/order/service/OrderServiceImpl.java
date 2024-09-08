package com.beerpub.order.service;

import com.beerpub.order.converter.OrderConverter;
import com.beerpub.order.dao.Order;
import com.beerpub.order.dao.OrderRepository;
import com.beerpub.order.dao.UserRepository;
import com.beerpub.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 根据用户ID创建订单，如果用户有未确认订单，返回未确认订单,即为购物车
    @Override
    public OrderDTO getCart(Integer userId){
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User doesn't exist!"));

        Optional<Order> pendingOrder = orderRepository.findByUserIdAndStatus(userId, "PENDING");

        if (pendingOrder.isPresent()) {
            return OrderConverter.convertOrder(pendingOrder.get()); // return current unconfirmed order
        } else {
            // initialize
            Order newOrder = new Order();
            newOrder.setUserId(userId);
            newOrder.setStatus("PENDING");
            newOrder.setOrderDate(LocalDateTime.now());
            newOrder.setTotalAmount(0);

            orderRepository.save(newOrder);
            return OrderConverter.convertOrder(newOrder);
        }
    }

    // get
    @Override
    public List<OrderDTO> getOrderByUserId(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User doesn't exist!"));
        List<Order> userList = orderRepository.findByUserId(userId);
        return userList.stream().map(OrderConverter::convertOrder).collect(Collectors.toList());
    }

    // delete
    @Override
    public void deleteOrderById(Integer orderId) {
        orderRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order doesn't exist!"));
        orderRepository.deleteById(orderId);
    }

    // confirm order
    @Override
    public OrderDTO confirmOrder(Integer orderId) {

        Order order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order doesn't exist!"));

        // change order status
        order.setStatus("CONFIRMED");
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        return OrderConverter.convertOrder(order);
    }
}
