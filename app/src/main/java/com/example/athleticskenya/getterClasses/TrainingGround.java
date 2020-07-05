package com.example.athleticskenya.getterClasses;

public class TrainingGround {

    private String ground_name;
    private String ground_location;
    private String ground_availability;
    private String ground_condition;
    private String ground_image;

    public TrainingGround(String ground_name, String ground_image, String ground_location, String ground_availability, String ground_condition) {

        this.ground_name = ground_name;
        this.ground_image = ground_image;
        this.ground_location = ground_location;
        this.ground_availability = ground_availability;
        this.ground_condition = ground_condition;
    }

    public String getGround_name() {
        return ground_name;
    }

    public String getGround_location() {
        return ground_location;
    }

    public String getGround_availability() {
        return ground_availability;
    }

    public String getGround_condition() {
        return ground_condition;
    }

    public String getGround_image() {
        return ground_image;
    }
}
