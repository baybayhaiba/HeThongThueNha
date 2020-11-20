package com.example.hethongthuenha.Model;

public class Ads {
    private String id;
    private String id_room;
    private String id_person;
    private String type_ads;
    private String bankCard;
    private Double price;

    public Ads() {
    }

    public Ads(String id, String id_room, String id_person, String type_ads, String bankCard, Double price) {
        this.id = id;
        this.id_room = id_room;
        this.id_person = id_person;
        this.type_ads = type_ads;
        this.bankCard = bankCard;
        this.price = price;
    }

    public String getId_person() {
        return id_person;
    }

    public void setId_person(String id_person) {
        this.id_person = id_person;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_room() {
        return id_room;
    }

    public void setId_room(String id_room) {
        this.id_room = id_room;
    }

    public String getType_ads() {
        return type_ads;
    }

    public void setType_ads(String type_ads) {
        this.type_ads = type_ads;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "id='" + id + '\'' +
                ", id_room='" + id_room + '\'' +
                ", type_ads='" + type_ads + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", price=" + price +
                '}';
    }
}
