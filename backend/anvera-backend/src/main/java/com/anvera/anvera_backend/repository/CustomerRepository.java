package com.anvera.anvera_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvera.anvera_backend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}