package com.example.athleticskenya.getterClasses;

public class CoachRequest {

    private int coach_id;
    private int athlete_id;
    private String contact;
    private String afirstname;
    private String alastname;


    public CoachRequest(int coach_id, int athlete_id, String contact, String afirstname, String alastname) {
        this.coach_id = coach_id;
        this.athlete_id = athlete_id;
        this.contact = contact;
        this.afirstname = afirstname;
        this.alastname = alastname;

    }

    public int getCoach_id() {
        return coach_id;
    }

    public int getAthlete_id() {
        return athlete_id;
    }

    public String getContact() {
        return contact;
    }

    public String getAfirstname() {
        return afirstname;
    }

    public String getAlastname() {
        return alastname;
    }


}
