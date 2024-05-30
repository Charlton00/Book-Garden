package com.example.initializer.orders;

import java.sql.Date;

//import jakarta.persistence.Entity;

//@Entity
public class CreateOrder {

    private String firstName;
    private String lastName;
    private String billingAddress;
    private String inputBillingAddress;
    private String billingCity;
    private String billingState;
    private String billingZip;
    private String shippingAddress;
    private String inputShippingAddress;
    private String shippingCity;
    private String shippingState;
    private String shippingZip;
    private String card;
    private String cardNumber;
    private String cardName;
    private String expirationDate;
    private boolean checked;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName; 
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getInputBillingAddress() {
        return inputBillingAddress;
    }

    public void setInputBillingAddress(String inputBillingAddress) {
        this.inputBillingAddress = inputBillingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getInputShippingAddress() {
        return inputShippingAddress;
    }

    public void setInputShippingAddress(String inputShippingAddress) {
        this.inputShippingAddress = inputShippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingZip() {
        return shippingZip;
    }

    public void setShippingZip(String shippingZip) {
        this.shippingZip = shippingZip;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
 
}