package helper;

public class Features
{
    private double calorie, protein, fat, carb;
    private String foodName;
    private double foodServingSize;
    private int foodImage;
    public Features(double calorie, double protein, double fat, double carb, String foodName, double foodServingSize, int foodImage) {
        this.calorie = calorie;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.foodName = foodName;
        this.foodServingSize = foodServingSize;
        this.foodImage = foodImage;
    }


    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodServingSize() {
        return foodServingSize;
    }

    public void setFoodServingSize(double foodServingSize) {
        this.foodServingSize = foodServingSize;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }
}