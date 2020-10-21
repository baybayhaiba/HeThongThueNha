package com.example.hethongthuenha.model;

public class Inkeeper extends Person{

    private String adress;

    public Inkeeper() {
    }

    public Inkeeper(String fullName, String origin, String contact,String adress) {
        super(fullName, origin, contact);
        this.adress=adress;
    }
}
