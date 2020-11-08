package com.example.hethongthuenha.Model;

public abstract class  Person {
    private String fullName;
    private String email;
    private String contact;

    public Person() { }

    public Person(String fullName, String email, String contact) {
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                " fullName='" + fullName + '\'' +
                ", origin='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
