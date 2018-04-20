package com.example.moumita.caloriecountergeb;

public class ShowFood {
    private String foodName, foodCalorie, foodServingSize;
    private int foodImage;

    public ShowFood(int foodImage, String foodName, String foodCalorie, String foodServingSize) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodCalorie = foodCalorie;
        this.foodServingSize = foodServingSize;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodCalorie() {
        return foodCalorie;
    }

    public String getFoodServingSize() {
        return foodServingSize;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }
}
