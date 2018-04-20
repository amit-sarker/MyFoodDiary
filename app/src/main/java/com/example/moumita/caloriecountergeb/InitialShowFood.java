package com.example.moumita.caloriecountergeb;

public class InitialShowFood {
    private int foodImage;
    private String motivationalSpeech;

    public InitialShowFood(int foodImage, String motivationalSpeech) {
        this.foodImage = foodImage;
        this.motivationalSpeech = motivationalSpeech;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getMotivationalSpeech() {
        return motivationalSpeech;
    }

    public void setMotivationalSpeech(String motivationalSpeech) {
        this.motivationalSpeech = motivationalSpeech;
    }
}
