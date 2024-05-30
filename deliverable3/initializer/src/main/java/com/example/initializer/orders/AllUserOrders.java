package com.example.initializer.orders;

import java.util.List;

//import jakarta.persistence.Entity;

//@Entity
public class AllUserOrders {
    
    private long id;
    private double totalPrice;
    List<OrderItemHistory> orderItems;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setOrderItems(List<OrderItemHistory> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItemHistory> getOrderItems() {
        return this.orderItems;
    }
}
