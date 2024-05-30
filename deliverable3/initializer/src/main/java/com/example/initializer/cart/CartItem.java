package com.example.initializer.cart;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
@DynamicUpdate
public class CartItem {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
	private long cartItemId;

    private long isbn;
    @Column(name= "cart_id")
    private long cartId;
    private int quantity;
    
    public long getCartItemId() {
        return cartItemId;
    }
    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }
    public long getIsbn() {
        return isbn;
    }
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }
    public long getCartId() {
        return cartId;
    }
    public void setCartId(long cartId) {
        this.cartId = cartId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
