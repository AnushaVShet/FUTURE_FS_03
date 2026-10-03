package com.anvera.anvera_backend.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.anvera.anvera_backend.entity.Order;
import com.anvera.anvera_backend.entity.OrderItem;
import com.anvera.anvera_backend.entity.Product;
import com.anvera.anvera_backend.repository.OrderRepository;
import com.anvera.anvera_backend.repository.OrderItemRepository;
import com.anvera.anvera_backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderController(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {

        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("Pending");
        }

        return orderRepository.save(order);
    }

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

    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(
            @PathVariable Integer orderId) {

        return orderItemRepository.findByOrderId(orderId);
    }

    @PostMapping("/{orderId}/items")
    @Transactional
    public OrderItem addOrderItem(
            @PathVariable Integer orderId,
            @RequestBody OrderItem item) {

        Product product =
                productRepository.findById(item.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        int requestedQuantity = item.getQuantity();

        if (requestedQuantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than zero");
        }

        if (product.getStock() < requestedQuantity) {
            throw new RuntimeException(
                    "Insufficient stock. Available stock: "
                    + product.getStock());
        }

        item.setOrderId(orderId);

        item.setPrice(product.getPrice());

        OrderItem savedItem =
                orderItemRepository.save(item);

        product.setStock(
                product.getStock() - requestedQuantity
        );

        productRepository.save(product);

        return savedItem;
    }
}