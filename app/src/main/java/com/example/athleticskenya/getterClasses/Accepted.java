package com.example.athleticskenya.getterClasses;

public class Accepted {

    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String d_o_b;
    private String race;
    private String image;
    private String contact;
    private String height;
    private String weight;
    private String location;

    public Accepted (int id, String firstname, String lastname, String phone, String email, String d_o_b, String race, String image, String contact,
    String height, String weight, String location) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.d_o_b = d_o_b;
        this.race = race;
        this.image = image;
        this.contact = contact;
        this.height = height;
        this.weight = weight;
        this.location = location;

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

    public String getD_o_b() {
        return d_o_b;
    }

    public String getRace() {
        return race;
    }

    public String getImage() {
        return image;
    }

    public String getContact() {
        return contact;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getLocation() {
        return location;
    }
}
