package com.example.moumita.caloriecountergeb;

public class Food {
    private long food_id, food_category_id;
    private String food_name, food_serving_mesurment, food_image, food_notes;
    private double food_serving_size, food_energy, food_proteins, food_carbohydrates,
            food_fat, food_energy_calculated, food_carbohydrates_calculated, food_proteins_calculated,
            food_fat_calculated;

    public Food() {
    }

    public Food(long food_id, String food_name, double food_serving_size, String food_serving_mesurment,
                double food_energy, double food_proteins, double food_carbohydrates, double food_fat,
                double food_energy_calculated, double food_proteins_calculated, double food_carbohydrates_calculated,
                double food_fat_calculated, long food_category_id, String food_image, String food_notes) {

        this.food_id = food_id;
        this.food_name = food_name;
        this.food_serving_size = food_serving_size;
        this.food_serving_mesurment = food_serving_mesurment;
        this.food_energy = food_energy;
        this.food_proteins = food_proteins;
        this.food_carbohydrates = food_carbohydrates;
        this.food_fat = food_fat;
        this.food_energy_calculated = food_energy_calculated;
        this.food_proteins_calculated = food_proteins_calculated;
        this.food_carbohydrates_calculated = food_carbohydrates_calculated;
        this.food_fat_calculated = food_fat_calculated;
        this.food_category_id = food_category_id;
        this.food_image = food_image;
        this.food_notes = food_notes;
    }

    public long getFood_id() {
        return food_id;
    }

    public void setFood_id(long food_id) {
        this.food_id = food_id;
    }

    public long getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(long food_category_id) {
        this.food_category_id = food_category_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_serving_mesurment() {
        return food_serving_mesurment;
    }

    public void setFood_serving_mesurment(String food_serving_mesurment) {
        this.food_serving_mesurment = food_serving_mesurment;
    }

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public String getFood_notes() {
        return food_notes;
    }

    public void setFood_notes(String food_notes) {
        this.food_notes = food_notes;
    }

    public double getFood_serving_size() {
        return food_serving_size;
    }

    public void setFood_serving_size(double food_serving_size) {
        this.food_serving_size = food_serving_size;
    }

    public double getFood_energy() {
        return food_energy;
    }

    public void setFood_energy(double food_energy) {
        this.food_energy = food_energy;
    }

    public double getFood_proteins() {
        return food_proteins;
    }

    public void setFood_proteins(double food_proteins) {
        this.food_proteins = food_proteins;
    }

    public double getFood_carbohydrates() {
        return food_carbohydrates;
    }

    public void setFood_carbohydrates(double food_carbohydrates) {
        this.food_carbohydrates = food_carbohydrates;
    }

    public double getFood_fat() {
        return food_fat;
    }

    public void setFood_fat(double food_fat) {
        this.food_fat = food_fat;
    }

    public double getFood_energy_calculated() {
        return food_energy_calculated;
    }

    public void setFood_energy_calculated(double food_energy_calculated) {
        this.food_energy_calculated = food_energy_calculated;
    }

    public double getFood_carbohydrates_calculated() {
        return food_carbohydrates_calculated;
    }

    public void setFood_carbohydrates_calculated(double food_carbohydrates_calculated) {
        this.food_carbohydrates_calculated = food_carbohydrates_calculated;
    }

    public double getFood_proteins_calculated() {
        return food_proteins_calculated;
    }

    public void setFood_proteins_calculated(double food_proteins_calculated) {
        this.food_proteins_calculated = food_proteins_calculated;
    }

    public double getFood_fat_calculated() {
        return food_fat_calculated;
    }

    public void setFood_fat_calculated(double food_fat_calculated) {
        this.food_fat_calculated = food_fat_calculated;
    }

    @Override
    public String toString() {
        return "Food{" +
                "food_id=" + food_id +
                ", food_category_id=" + food_category_id +
                ", food_name='" + food_name + '\'' +
                ", food_serving_mesurment='" + food_serving_mesurment + '\'' +
                ", food_image='" + food_image + '\'' +
                ", food_notes='" + food_notes + '\'' +
                ", food_serving_size=" + food_serving_size +
                ", food_energy=" + food_energy +
                ", food_proteins=" + food_proteins +
                ", food_carbohydrates=" + food_carbohydrates +
                ", food_fat=" + food_fat +
                ", food_energy_calculated=" + food_energy_calculated +
                ", food_carbohydrates_calculated=" + food_carbohydrates_calculated +
                ", food_proteins_calculated=" + food_proteins_calculated +
                ", food_fat_calculated=" + food_fat_calculated +
                '}';
    }
}
