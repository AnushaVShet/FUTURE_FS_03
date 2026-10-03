package com.anvera.anvera_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.anvera.anvera_backend.entity.Order;
import com.anvera.anvera_backend.repository.OrderRepository;

@RestController
@RequestMapping("/api/admin/protected/orders")
public class AdminOrderController {

    private final OrderRepository orderRepository;

    public AdminOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update order status
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(
            @PathVariable Integer id,
            @RequestBody Order order) {

        Order existingOrder =
                orderRepository.findById(id).orElse(null);

        if (existingOrder == null) {
            return null;
        }

        existingOrder.setStatus(order.getStatus());

        return orderRepository.save(existingOrder);
    }
}