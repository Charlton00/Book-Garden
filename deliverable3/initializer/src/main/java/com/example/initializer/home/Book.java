package com.example.initializer.home;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
@DynamicUpdate
public class Book {

	private String title;

    @Id
	private long isbn;

	private String authors;

	private String category;

	private String cover;

    private String edition;

    private String publisher;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "quantity_in_stock")
    private int quantityInStock;

	@Column(name = "buying_price")
	private double buyingPrice;

	@Column(name = "selling_price")
	private double sellingPrice;

	@Column(name = "minimum_threshold")
	private double minThreshold;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getTitle() {
        return title;
    }

    public Long getIsbn() {
        return isbn;
    }

    public String getAuthors() {
        return authors;
    }

    public String getCategory() {
        return category;
    }

    public String getCover() {
        return cover;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

}
