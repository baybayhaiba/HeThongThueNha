package com.example.hethongthuenha.Model;

public class Chat {
    private int id_chat;
    private String text;
    private String from_email_person;
    private String to_email_person;


    public Chat() {
    }

    public Chat(int id_chat, String text, String from_id_person, String to_id_person) {
        this.id_chat = id_chat;
        this.text = text;
        this.from_email_person = from_id_person;
        this.to_email_person = to_id_person;
    }

    public int getId_chat() {
        return id_chat;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom_email_person() {
        return from_email_person;
    }

    public void setFrom_email_person(String from_email_person) {
        this.from_email_person = from_email_person;
    }

    public String getTo_email_person() {
        return to_email_person;
    }

    public void setTo_email_person(String to_email_person) {
        this.to_email_person = to_email_person;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id_chat='" + id_chat + '\'' +
                ", text='" + text + '\'' +
                ", from_id_person='" + from_email_person + '\'' +
                ", to_id_person='" + to_email_person + '\'' +
                '}';
    }
}
