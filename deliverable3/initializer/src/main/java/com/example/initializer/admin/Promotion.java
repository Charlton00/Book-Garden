package com.example.initializer.admin;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotion")
@DynamicUpdate
public class Promotion {
    
    @Id
    @Column(name = "promotion_id")
    private String promotionId;
    private int discount;
    @Column(name = "end_time")
    private String endTime;
    private int sent;
    
    public String getPromotionId() {
        return promotionId;
    }
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public int getSent() {
        return sent;
    }
    public void setSent(int sent) {
        this.sent = sent;
    }
}
