package com.example.eatproductmanager.Domain;

public class OrderDetailDomain {
    private String foodID, orderID;
    private Integer quantity, unitSellingPrice;

    public OrderDetailDomain() {
    }

    public OrderDetailDomain(String foodID, String orderID, Integer quantity, Integer unitSellingPrice) {
        this.foodID = foodID;
        this.orderID = orderID;
        this.quantity = quantity;
        this.unitSellingPrice = unitSellingPrice;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(Integer unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }
}
