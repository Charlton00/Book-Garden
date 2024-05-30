package com.example.initializer.cart;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Table(name="shopping_cart")
public class UserCart implements Serializable{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long isbn;

    private int quantityInCart;
    private String authors;
    private String title;
    private String cover;
    private double sellingPrice;
    
    public long getIsbn() {
        return isbn;
    }
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }
    public int getQuantityInCart() {
        return quantityInCart;
    }
    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }
    public String getAuthors() {
        return authors;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public double getSellingPrice() {
        return sellingPrice;
    }
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

}
