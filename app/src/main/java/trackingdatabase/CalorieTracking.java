package trackingdatabase;

public class CalorieTracking {
    private long calorie_tracking_id;
    private String date;
    private double cal_needed, cal_consumed, cal_remaining, protein_needed, protein_consumed, protein_remaining,
            fat_needed, fat_consumed, fat_remaining, carbs_needed, carbs_consumed, carbs_remaining, water_consumed;
    private long goal_point, rank;

    public CalorieTracking() {}

    public CalorieTracking(long calorie_tracking_id, String date, double cal_needed, double cal_consumed,
                           double cal_remaining, double protein_needed, double protein_consumed, double protein_remaining,
                           double fat_needed, double fat_consumed, double fat_remaining, double carbs_needed, double carbs_consumed,
                           double carbs_remaining, double water_consumed, long goal_point, long rank) {

        this.calorie_tracking_id = calorie_tracking_id;
        this.date = date;
        this.cal_needed = cal_needed;
        this.cal_consumed = cal_consumed;
        this.cal_remaining = cal_remaining;
        this.protein_needed = protein_needed;
        this.protein_consumed = protein_consumed;
        this.protein_remaining = protein_remaining;
        this.fat_needed = fat_needed;
        this.fat_consumed = fat_consumed;
        this.fat_remaining = fat_remaining;
        this.carbs_needed = carbs_needed;
        this.carbs_consumed = carbs_consumed;
        this.carbs_remaining = carbs_remaining;
        this.water_consumed = water_consumed;
        this.goal_point = goal_point;
        this.rank = rank;
    }

    public long getCalorie_tracking_id() {
        return calorie_tracking_id;
    }

    public void setCalorie_tracking_id(long calorie_tracking_id) {
        this.calorie_tracking_id = calorie_tracking_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCal_needed() {
        return cal_needed;
    }

    public void setCal_needed(double cal_needed) {
        this.cal_needed = cal_needed;
    }

    public double getCal_consumed() {
        return cal_consumed;
    }

    public void setCal_consumed(double cal_consumed) {
        this.cal_consumed = cal_consumed;
    }

    public double getCal_remaining() {
        return cal_remaining;
    }

    public void setCal_remaining(double cal_remaining) {
        this.cal_remaining = cal_remaining;
    }

    public double getProtein_needed() {
        return protein_needed;
    }

    public void setProtein_needed(double protein_needed) {
        this.protein_needed = protein_needed;
    }

    public double getProtein_consumed() {
        return protein_consumed;
    }

    public void setProtein_consumed(double protein_consumed) {
        this.protein_consumed = protein_consumed;
    }

    public double getProtein_remaining() {
        return protein_remaining;
    }

    public void setProtein_remaining(double protein_remaining) {
        this.protein_remaining = protein_remaining;
    }

    public double getFat_needed() {
        return fat_needed;
    }

    public void setFat_needed(double fat_needed) {
        this.fat_needed = fat_needed;
    }

    public double getFat_consumed() {
        return fat_consumed;
    }

    public void setFat_consumed(double fat_consumed) {
        this.fat_consumed = fat_consumed;
    }

    public double getFat_remaining() {
        return fat_remaining;
    }

    public void setFat_remaining(double fat_remaining) {
        this.fat_remaining = fat_remaining;
    }

    public double getCarbs_needed() {
        return carbs_needed;
    }

    public void setCarbs_needed(double carbs_needed) {
        this.carbs_needed = carbs_needed;
    }

    public double getCarbs_consumed() {
        return carbs_consumed;
    }

    public void setCarbs_consumed(double carbs_consumed) {
        this.carbs_consumed = carbs_consumed;
    }

    public double getCarbs_remaining() {
        return carbs_remaining;
    }

    public void setCarbs_remaining(double carbs_remaining) {
        this.carbs_remaining = carbs_remaining;
    }

    public double getWater_consumed() {
        return water_consumed;
    }

    public void setWater_consumed(double water_consumed) {
        this.water_consumed = water_consumed;
    }

    public long getGoal_point() {
        return goal_point;
    }

    public void setGoal_point(long goal_point) {
        this.goal_point = goal_point;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "CalorieTracking{" +
                "calorie_tracking_id=" + calorie_tracking_id +
                ", date='" + date + '\'' +
                ", cal_needed=" + cal_needed +
                ", cal_consumed=" + cal_consumed +
                ", cal_remaining=" + cal_remaining +
                ", protein_needed=" + protein_needed +
                ", protein_consumed=" + protein_consumed +
                ", protein_remaining=" + protein_remaining +
                ", fat_needed=" + fat_needed +
                ", fat_consumed=" + fat_consumed +
                ", fat_remaining=" + fat_remaining +
                ", carbs_needed=" + carbs_needed +
                ", carbs_consumed=" + carbs_consumed +
                ", carbs_remaining=" + carbs_remaining +
                ", water_consumed=" + water_consumed +
                ", goal_point=" + goal_point +
                ", rank=" + rank +
                '}';
    }
}
