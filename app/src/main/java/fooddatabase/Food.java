package fooddatabase;

public class Food {
    private long food_id;
    private String food_name;
    private double food_serving_size;
    private String food_serving_mesurment;
    private double food_energy, food_proteins, food_carbohydrates, food_fat, food_water, food_fiber,
            food_vit_a, food_vit_c, food_vit_e;
    private long food_category_id;
    private String food_image, food_notes;

    public Food() {
    }

    public Food(long food_id, String food_name, double food_serving_size, String food_serving_mesurment, double food_energy,
                double food_proteins, double food_carbohydrates, double food_fat, double food_water, double food_fiber,
                double food_vit_a, double food_vit_c, double food_vit_e, long food_category_id, String food_image,
                String food_notes) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_serving_size = food_serving_size;
        this.food_serving_mesurment = food_serving_mesurment;
        this.food_energy = food_energy;
        this.food_proteins = food_proteins;
        this.food_carbohydrates = food_carbohydrates;
        this.food_fat = food_fat;
        this.food_water = food_water;
        this.food_fiber = food_fiber;
        this.food_vit_a = food_vit_a;
        this.food_vit_c = food_vit_c;
        this.food_vit_e = food_vit_e;
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

    public double getFood_water() {
        return food_water;
    }

    public void setFood_water(double food_water) {
        this.food_water = food_water;
    }

    public double getFood_fiber() {
        return food_fiber;
    }

    public void setFood_fiber(double food_fiber) {
        this.food_fiber = food_fiber;
    }

    public double getFood_vit_a() {
        return food_vit_a;
    }

    public void setFood_vit_a(double food_vit_a) {
        this.food_vit_a = food_vit_a;
    }

    public double getFood_vit_e() {
        return food_vit_e;
    }

    public void setFood_vit_e(double food_vit_e) {
        this.food_vit_e = food_vit_e;
    }

    public double getFood_vit_c() {
        return food_vit_c;
    }

    public void setFood_vit_c(double food_vit_c) {
        this.food_vit_c = food_vit_c;
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
                ", food_water=" + food_water +
                ", food_fiber=" + food_fiber +
                ", food_vit_a=" + food_vit_a +
                ", food_vit_e=" + food_vit_e +
                ", food_vit_c=" + food_vit_c +
                '}';
    }
}
