package servingdatabase;

public class FoodServing {
    long serving_id;
    String food_name, food_serving_measurement, serving_size_to_grams;

    public FoodServing() {}

    public FoodServing(long serving_id, String food_name, String food_serving_measurement, String serving_size_to_grams) {
        this.serving_id = serving_id;
        this.food_name = food_name;
        this.food_serving_measurement = food_serving_measurement;
        this.serving_size_to_grams = serving_size_to_grams;
    }

    public long getServing_id() {
        return serving_id;
    }

    public void setServing_id(long serving_id) {
        this.serving_id = serving_id;
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

    public String getServing_size_to_grams() {
        return serving_size_to_grams;
    }

    public void setServing_size_to_grams(String serving_size_to_grams) {
        this.serving_size_to_grams = serving_size_to_grams;
    }

    @Override
    public String toString() {
        return "FoodServing{" +
                "serving_id=" + serving_id +
                ", food_name='" + food_name + '\'' +
                ", food_serving_measurement='" + food_serving_measurement + '\'' +
                ", serving_size_to_grams='" + serving_size_to_grams + '\'' +
                '}';
    }
}
