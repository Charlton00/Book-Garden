package com.example.initializer.orders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@DynamicUpdate
public class Orders {
    
    @Id
    @Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
    
    @Column(name = "user_id")
	private long userId;
	
    @Column(name = "order_time")
	private Timestamp orderTime;
	
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
    @Column(name = "sub_total")
	private double subtotal;
	
    @Column(name = "promotion_id")
	private long promotionId;
    
    @Column(name = "total_price")
    private double totalPrice;
    
    @Column(name = "payment_id")
    private long paymentId;
    
    @Column(name = "shipping_address")
    private long shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems= new ArrayList<>();

    public long getOrderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public long getPromotionId() {
        return promotionId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public long getShippingAddress() {
        return shippingAddress;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public void setShippingAddress(long shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
