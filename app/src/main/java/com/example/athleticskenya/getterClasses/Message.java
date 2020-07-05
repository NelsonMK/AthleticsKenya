package com.example.athleticskenya.getterClasses;

public class Message  {

    private int id;
    private String receiver_id, fromName, message, contact, createdAt;

    public Message(int id, String receiver_id, String fromName, String message, String contact, String createdAt) {
        this.id = id;
        this.receiver_id = receiver_id;
        this.fromName = fromName;
        this.message = message;
        this.contact = contact;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public String getFromName() {
        return fromName;
    }

    public String getMessage() {
        return message;
    }

    public String getContact() {
        return contact;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
