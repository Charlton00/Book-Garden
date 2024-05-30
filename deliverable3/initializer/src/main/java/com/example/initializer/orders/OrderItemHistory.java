package com.example.initializer.orders;

public class OrderItemHistory {
    
    private long isbn;
    private String title;
    private String author;
    private double totalPrice;
    private int copies;
    private String cover;

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public long getIsbn() {
        return this.isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getCopies() {
        return this.copies;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return this.cover;
    }
}
