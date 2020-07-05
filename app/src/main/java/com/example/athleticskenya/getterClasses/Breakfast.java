package com.example.athleticskenya.getterClasses;

public class Breakfast {

    private String food;
    private String brand;
    private String calories;
    private String carbohydrates;
    private String proteins;
    private String fats;

    public Breakfast (String food, String brand, String calories, String carbohydrates, String proteins, String fats) {

        this.food = food;
        this.brand = brand;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
    }

    public String getFood() {
        return food;
    }

    public String getBrand() {
        return brand;
    }

    public String getCalories() {
        return calories;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public String getProteins() {
        return proteins;
    }

    public String getFats() {
        return fats;
    }
}
