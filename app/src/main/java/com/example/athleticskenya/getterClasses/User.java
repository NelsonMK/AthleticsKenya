package com.example.athleticskenya.getterClasses;

public class User {

    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String date_of_birth;
    private String race_type;
    private int id;
    private String contact;
    private String image;
    private int class_id;
    private int status;
    private int archive;
    private String height;
    private String weight;
    private String location;

    public User(){

    }

    public User (int id, String firstname, String lastname, String phone, String email, String date_of_birth, String race_type, String image, int class_id, int status, int archive, String contact,
    String height, String weight, String location) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.race_type = race_type;
        this.image = image;
        this.class_id = class_id;
        this.status = status;
        this.archive = archive;
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

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getRace_type() {
        return race_type;
    }

    public String getContact() {
        return contact;
    }

    public int getClass_id() {
        return class_id;
    }

    public int getStatus() {
        return status;
    }

    public int getArchive() {
        return archive;
    }

    public String getImage() {
        return image;
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setRace_type(String race_type) {
        this.race_type = race_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
