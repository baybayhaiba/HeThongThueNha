package com.example.hethongthuenha.model;

public abstract class  Person {
    private int id;
    private String fullName;
    private String origin;
    private String contact;
    private static int count;

    public Person() { }

    public Person(String fullName, String origin, String contact) {
        ++count;

        this.id = count;
        this.fullName = fullName;
        this.origin = origin;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", origin='" + origin + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
