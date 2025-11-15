package com.dreamscene.repository;

import com.dreamscene.entity.SpecialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialOrderRepository extends JpaRepository<SpecialOrder, Long> {
    List<SpecialOrder> findByUserEmailOrderByCreatedAtDesc(String userEmail);
    List<SpecialOrder> findAllByOrderByCreatedAtDesc();
}
