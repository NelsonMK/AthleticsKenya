package com.example.athleticskenya.getterClasses;

public class Awards {

    private String athlete_f_name, athlete_l_name, athlete_race, position, medal, image;

    public Awards(String athlete_f_name, String athlete_l_name, String athlete_race ,String position ,String medal, String image){
        this.athlete_f_name = athlete_f_name;
        this.athlete_l_name = athlete_l_name;
        this.athlete_race = athlete_race;
        this.position = position;
        this.medal = medal;
        this.image = image;
    }

    public String getAthlete_f_name() {
        return athlete_f_name;
    }

    public String getAthlete_l_name() {
        return athlete_l_name;
    }

    public String getAthlete_race() {
        return athlete_race;
    }

    public String getPosition() {
        return position;
    }

    public String getMedal() {
        return medal;
    }

    public String getImage() {
        return image;
    }
}
