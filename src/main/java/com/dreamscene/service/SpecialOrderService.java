package com.dreamscene.service;

import com.dreamscene.dto.request.SpecialOrderRequest;
import com.dreamscene.entity.SpecialOrder;
import com.dreamscene.repository.SpecialOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialOrderService {
    
    private final SpecialOrderRepository specialOrderRepository;
    
    public List<SpecialOrder> getAllSpecialOrders() {
        return specialOrderRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public SpecialOrder getSpecialOrderById(Long id) {
        return specialOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Special order not found with id: " + id));
    }
    
    public List<SpecialOrder> getSpecialOrdersByEmail(String email) {
        return specialOrderRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }
    
    @Transactional
    public SpecialOrder createSpecialOrder(SpecialOrderRequest request) {
        SpecialOrder specialOrder = new SpecialOrder();
        specialOrder.setUserName(request.getUserName());
        specialOrder.setUserEmail(request.getUserEmail());
        specialOrder.setDescription(request.getDescription());
        specialOrder.setImageUrls(request.getImageUrls());
        specialOrder.setStatus(SpecialOrder.OrderStatus.PENDING);
        return specialOrderRepository.save(specialOrder);
    }
    
    @Transactional
    public SpecialOrder updateStatus(Long id, SpecialOrder.OrderStatus status, String adminNotes) {
        SpecialOrder specialOrder = getSpecialOrderById(id);
        specialOrder.setStatus(status);
        if (adminNotes != null) {
            specialOrder.setAdminNotes(adminNotes);
        }
        return specialOrderRepository.save(specialOrder);
    }
    
    @Transactional
    public void deleteSpecialOrder(Long id) {
        specialOrderRepository.deleteById(id);
    }
}
