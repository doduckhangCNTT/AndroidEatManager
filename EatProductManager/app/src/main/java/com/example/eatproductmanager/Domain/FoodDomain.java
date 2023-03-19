package com.example.eatproductmanager.Domain;

public class FoodDomain {
    private String CategoryID, Description,
            Discount,
            Image, Name, Price;

    public FoodDomain() {
    }

    public FoodDomain(String categoryID, String description, String discount, String image, String name, String price) {
        CategoryID = categoryID;
        Description = description;
        Discount = discount;
        Image = image;
        Name = name;
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

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
