package com.example.eatproductmanager.Domain;

public class CategoryDomain {
    private String Name, Image;

    private String CategoryID;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public CategoryDomain() {
    }

//    public CategoryDomain(String name, String image) {
//        Name = name;
//        Image = image;
//    }


    public CategoryDomain(String name, String image, String categoryID) {
        Name = name;
        Image = image;
        CategoryID = categoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
