package com.example.eatproductmanager.Domain;

public class PaymentDomain {
    private String name;

    public PaymentDomain() {
    }

    public PaymentDomain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
