package com.dreamscene.controller;

import com.dreamscene.dto.request.PartnerRequest;
import com.dreamscene.dto.response.ApiResponse;
import com.dreamscene.entity.Partner;
import com.dreamscene.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PartnerController {
    
    private final PartnerService partnerService;
    
    // Public endpoint
    @GetMapping("/partners")
    public ResponseEntity<ApiResponse<List<Partner>>> getAllPartners() {
        List<Partner> partners = partnerService.getAllPartners();
        return ResponseEntity.ok(ApiResponse.success("Partners retrieved successfully", partners));
    }
    
    @GetMapping("/partners/{id}")
    public ResponseEntity<ApiResponse<Partner>> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerService.getPartnerById(id);
        return ResponseEntity.ok(ApiResponse.success("Partner retrieved successfully", partner));
    }
    
    // Admin endpoints
    @PostMapping("/admin/partners")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Partner>> createPartner(@Valid @RequestBody PartnerRequest request) {
        Partner partner = partnerService.createPartner(request);
        return ResponseEntity.ok(ApiResponse.success("Partner created successfully", partner));
    }
    
    @PutMapping("/admin/partners/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Partner>> updatePartner(
            @PathVariable Long id,
            @Valid @RequestBody PartnerRequest request) {
        Partner partner = partnerService.updatePartner(id, request);
        return ResponseEntity.ok(ApiResponse.success("Partner updated successfully", partner));
    }
    
    @DeleteMapping("/admin/partners/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok(ApiResponse.success("Partner deleted successfully", null));
    }
}