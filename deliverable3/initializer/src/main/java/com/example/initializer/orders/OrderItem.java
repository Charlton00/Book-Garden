package com.example.initializer.orders;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
@DynamicUpdate
public class OrderItem {
    @Id
    @Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderItemId;

    @Column(name = "order_id", insertable = false, updatable = false)
	private long orderId;

    @Column(name = "isbn")
	private long isbn;

    @Column(name = "quantity")
	private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    public long getOrderItemId() {
        return orderItemId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
