package com.example.hethongthuenha.model;

import java.util.List;

public class Room {
    private String title;
    private String description;
    private String price;
    private int amout;
    private int accommodation;
    private List<String> utilities;
    private List<String> livingExpenses;
    private String homeOwner;
    private String address;

    public Room(String title, String description, String price, int amout, int accommodation, List<String> utilities, List<String> livingExpenses, String homeOwner, String address) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.amout = amout;
        this.accommodation = accommodation;
        this.utilities = utilities;
        this.livingExpenses = livingExpenses;
        this.homeOwner = homeOwner;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public int getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(int accommodation) {
        this.accommodation = accommodation;
    }

    public List<String> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<String> utilities) {
        this.utilities = utilities;
    }

    public List<String> getLivingExpenses() {
        return livingExpenses;
    }

    public void setLivingExpenses(List<String> livingExpenses) {
        this.livingExpenses = livingExpenses;
    }

    public String getHomeOwner() {
        return homeOwner;
    }

    public void setHomeOwner(String homeOwner) {
        this.homeOwner = homeOwner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Room{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", amout=" + amout +
                ", accommodation=" + accommodation +
                ", utilities=" + utilities +
                ", livingExpenses=" + livingExpenses +
                ", homeOwner='" + homeOwner + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
