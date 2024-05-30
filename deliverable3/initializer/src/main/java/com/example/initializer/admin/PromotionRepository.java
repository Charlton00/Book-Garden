package com.example.initializer.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, String> {
    
    Promotion findByPromotionId(String promotionId);
    
}
