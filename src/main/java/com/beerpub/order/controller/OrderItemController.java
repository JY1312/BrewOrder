package com.beerpub.order.controller;

import com.beerpub.order.Response;
import com.beerpub.order.dto.OrderItemDTO;
import com.beerpub.order.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    // 获取订单项
    @GetMapping("/orders/{orderId}/items")
    public Response getOrderItemsByOrderId(@PathVariable Integer orderId) {
        try {
            List<OrderItemDTO> orderItems = orderItemService.getOrderByUserId(orderId);
            return Response.newSuccess(orderItems);
        } catch (Exception e) {
            return Response.newFail("Failed to fetch order items: " + e.getMessage());
        }
    }

    // 添加订单项或增加数量
    @PostMapping("/orders/{orderId}/item/{itemId}/add")
    public Response addOrderItem(@PathVariable Integer orderId, @PathVariable Integer itemId) {
        try {
            OrderItemDTO orderItem = orderItemService.addOrderItem(orderId, itemId);
            return Response.newSuccess(orderItem);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (RuntimeException e) {
            return Response.newFail("Item is not available: " + e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to add order item: " + e.getMessage());
        }
    }

    // 删除订单项
    @DeleteMapping("orders/items/{orderItemId}")
    public Response<Void> deleteOrderItem(@PathVariable Integer orderItemId) {
        try {
            orderItemService.deleteOrderItem(orderItemId);
            return Response.newSuccess(null);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to delete order item: " + e.getMessage());
        }
    }

    // 减少订单项
    @PostMapping("/orders/items/{orderItemId}/decrease")
    public Response decreaseOrderItemQuantity(@PathVariable Integer orderItemId) {
        try {
            OrderItemDTO orderItem = orderItemService.decreaseOrderItemQuantity(orderItemId);
            return Response.newSuccess(orderItem);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to decrease order item quantity: " + e.getMessage());
        }
    }
}
