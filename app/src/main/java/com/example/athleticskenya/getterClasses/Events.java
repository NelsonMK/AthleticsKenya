package com.example.athleticskenya.getterClasses;

public class Events {

    private String eventname;
    private String image;
    private String location;
    private String date;
    private String time;
    private String details;
    private String more_details;

    public Events() {}

    public Events (String eventname, String image, String location, String date, String time, String details, String more_details) {

        this.eventname = eventname;
        this.image = image;
        this.location = location;
        this.date = date;
        this.time =time;
        this.details = details;
        this.more_details = more_details;
    }

    public String getEventname() {
        return eventname;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDetails() {
        return details;
    }

    public String getMore_details() {
        return more_details;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setMore_details(String more_details) {
        this.more_details = more_details;
    }
}
