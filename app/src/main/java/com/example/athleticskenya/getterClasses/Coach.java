package com.example.athleticskenya.getterClasses;

public class Coach {
    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String image;
    private String contact;

    public Coach(int id, String firstname, String lastname, String phone, String email, String image, String contact) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getImage(){
        return image;
    }

    public String getContact() {
        return contact;
    }
}
