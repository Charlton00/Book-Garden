package com.example.initializer.registration;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_info")
@DynamicUpdate
public class PaymentInfo {

	@Id
	@Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long paymentId;
    @Column(name = "user_id")
	private long userId;
	@Column(name = "hashed_card_num")
	private String hashedCardNumber;
	@Column(name = "card_type")
	private String cardType;
	@Column(name = "experation")
	private String expiration;
	@Column(name = "billing_address")
	private long billingAddress;
    public long getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getHashedCardNumber() {
        return hashedCardNumber;
    }
    public void setHashedCardNumber(String hashedCardNumber) {
        this.hashedCardNumber = hashedCardNumber;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getExpiration() {
        return expiration;
    }
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    public long getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(long billingAddress) {
        this.billingAddress = billingAddress;
    }

	
}
