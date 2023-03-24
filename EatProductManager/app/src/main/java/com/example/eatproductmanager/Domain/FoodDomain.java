package com.example.eatproductmanager.Domain;

public class FoodDomain {
    private String CategoryID, Description, Image, Name;
    private int Discount, Price;

    public FoodDomain() {
    }

    public FoodDomain(String categoryID, String description, String image, String name, int discount, int price) {
        CategoryID = categoryID;
        Description = description;
        Image = image;
        Name = name;
        Discount = discount;
        Price = price;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
