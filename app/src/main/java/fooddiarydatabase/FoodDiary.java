package fooddiarydatabase;

import java.util.Date;

public class FoodDiary {
    private long diary_id;
    private String food_name, food_serving_measurement, food_serving_amount, meal_type, total_cal_selected_food;
    private String date;

    public FoodDiary() {}

    public FoodDiary(long diary_id, String food_name, String food_serving_measurement, String food_serving_amount, String meal_type, String total_cal_selected_food, String date) {
        this.diary_id = diary_id;
        this.food_name = food_name;
        this.food_serving_measurement = food_serving_measurement;
        this.food_serving_amount = food_serving_amount;
        this.meal_type = meal_type;
        this.total_cal_selected_food = total_cal_selected_food;
        this.date = date;
    }

    public long getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(long diary_id) {
        this.diary_id = diary_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_serving_measurement() {
        return food_serving_measurement;
    }

    public void setFood_serving_measurement(String food_serving_measurement) {
        this.food_serving_measurement = food_serving_measurement;
    }

    public String getFood_serving_amount() {
        return food_serving_amount;
    }

    public void setFood_serving_amount(String food_serving_amount) {
        this.food_serving_amount = food_serving_amount;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_cal_selected_food() {
        return total_cal_selected_food;
    }

    public void setTotal_cal_selected_food(String total_cal_selected_food) {
        this.total_cal_selected_food = total_cal_selected_food;
    }

    @Override
    public String toString() {
        return "FoodDiary{" +
                "diary_id=" + diary_id +
                ", food_name='" + food_name + '\'' +
                ", food_serving_measurement='" + food_serving_measurement + '\'' +
                ", food_serving_amount='" + food_serving_amount + '\'' +
                ", meal_type='" + meal_type + '\'' +
                ", total_cal_selected_food='" + total_cal_selected_food + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
