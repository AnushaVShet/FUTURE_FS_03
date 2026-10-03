package com.anvera.anvera_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.anvera.anvera_backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}