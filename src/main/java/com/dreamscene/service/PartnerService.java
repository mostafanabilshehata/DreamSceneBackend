package com.dreamscene.service;

import com.dreamscene.dto.request.PartnerRequest;
import com.dreamscene.entity.Partner;
import com.dreamscene.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    
    private final PartnerRepository partnerRepository;
    
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }
    
    public Partner getPartnerById(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
    }
    
    @Transactional
    public Partner createPartner(PartnerRequest request) {
        Partner partner = new Partner();
        partner.setTitle(request.getTitle());
        partner.setCategory(request.getCategory());
        partner.setDescription(request.getDescription());
        partner.setIcon(request.getIcon());
        partner.setImageUrl(request.getImageUrl());
        partner.setSince(request.getSince());
        partner.setRating(request.getRating());
        partner.setGradient(request.getGradient());
        return partnerRepository.save(partner);
    }
    
    @Transactional
    public Partner updatePartner(Long id, PartnerRequest request) {
        Partner partner = getPartnerById(id);
        partner.setTitle(request.getTitle());
        partner.setCategory(request.getCategory());
        partner.setDescription(request.getDescription());
        partner.setIcon(request.getIcon());
        partner.setImageUrl(request.getImageUrl());
        partner.setSince(request.getSince());
        partner.setRating(request.getRating());
        partner.setGradient(request.getGradient());
        return partnerRepository.save(partner);
    }
    
    @Transactional
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}