package com.example.eatproductmanager.Domain;

public class OrderDomain {
    private String addressID, orderDate, orderID, paymentID, status, userID;
    private Integer deliveryFee, totalPrice;

    public OrderDomain() {
    }

    public OrderDomain(String addressID, String orderDate, String orderID, String paymentID, String status, String userID, Integer deliveryFee, Integer totalPrice) {
        this.addressID = addressID;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.paymentID = paymentID;
        this.status = status;
        this.userID = userID;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
