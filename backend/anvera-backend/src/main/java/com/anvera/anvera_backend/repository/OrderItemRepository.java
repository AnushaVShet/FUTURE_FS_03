package com.anvera.anvera_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvera.anvera_backend.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderId(Integer orderId);
}