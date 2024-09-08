package com.beerpub.order.controller;

import com.beerpub.order.dto.OrderDTO;
import com.beerpub.order.service.OrderService;
import com.beerpub.order.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}/cart")
    public Response getCart(@PathVariable Integer userId) {
        try {
            return Response.newSuccess(orderService.getCart(userId));
        } catch (Exception e) {
            return Response.newFail("Failed to get cart: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/orders")
    public Response getOrdersByUserId(@PathVariable Integer userId) {
        try {
            List<OrderDTO> orders = orderService.getOrderByUserId(userId);
            return Response.newSuccess(orders);
        } catch (Exception e) {
            return Response.newFail("Failed to fetch orders: " + e.getMessage());
        }
    }

    @DeleteMapping("orders/{orderId}")
    public Response<Void> deleteOrderById(@PathVariable Integer orderId) {
        try {
            orderService.deleteOrderById(orderId);
            return Response.newSuccess(null);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to delete order: " + e.getMessage());
        }
    }

    @PostMapping("orders/confirm/{orderId}")
    public Response confirmOrder(@PathVariable Integer orderId) {
        try {
            OrderDTO confirmedOrder = orderService.confirmOrder(orderId);
            return Response.newSuccess(confirmedOrder);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to confirm order: " + e.getMessage());
        }
    }


}
