package com.example.eatproductmanager.Domain;

public class UserDomain {
    private String Name, Password, Phone, Role;

    public UserDomain() {
    }

    public UserDomain(String name, String password, String phone, String role) {
        Name = name;
        Password = password;
        Phone = phone;
        Role = role;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
