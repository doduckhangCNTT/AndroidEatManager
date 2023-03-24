package com.example.eatproductmanager.Domain;

public class CategoryDomain {
    private String Name, Image;
    private String CategoryID;
    boolean isChecked = false;

    public CategoryDomain() {
    }


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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
