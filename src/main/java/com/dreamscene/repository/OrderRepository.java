package com.dreamscene.repository;

import com.dreamscene.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEmailOrderByCreatedAtDesc(String userEmail);
    List<Order> findByUserPhoneOrderByCreatedAtDesc(String userPhone);
    List<Order> findAllByOrderByCreatedAtDesc();
}
