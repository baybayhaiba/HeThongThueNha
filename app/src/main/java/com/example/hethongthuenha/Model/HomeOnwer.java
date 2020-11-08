package com.example.hethongthuenha.Model;

public class HomeOnwer extends Person{

    private String address;

    public HomeOnwer() {
    }

    public HomeOnwer(String fullName, String origin, String contact, String address) {
        super(fullName, origin, contact);
        this.address =address;
    }

    @Override
    public String toString() {
        return "HomeOnwer{" +
                "adress='" + address + '\'' +
                '}';
    }
}
