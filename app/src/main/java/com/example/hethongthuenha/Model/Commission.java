package com.example.hethongthuenha.Model;

import com.google.firebase.Timestamp;

public class Commission {
    private String id;
    private String id_person;
    private Double price;
    private Timestamp commissionAdded;

    public Commission() {
    }

    public Commission(String id, String id_person, Double price, Timestamp commissionAdded) {
        this.id = id;
        this.id_person = id_person;
        this.price = price;
        this.commissionAdded = commissionAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_person() {
        return id_person;
    }

    public void setId_person(String id_person) {
        this.id_person = id_person;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getCommissionAdded() {
        return commissionAdded;
    }

    public void setCommissionAdded(Timestamp commissionAdded) {
        this.commissionAdded = commissionAdded;
    }
}
