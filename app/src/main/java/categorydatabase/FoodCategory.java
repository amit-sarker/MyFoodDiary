package categorydatabase;

public class FoodCategory {
    private long categoryID;
    private String categoryName;
    private long foodID;
    private String foodName, foodImage, categoryImage;
    public FoodCategory() {}

    public FoodCategory(long categoryID, String categoryName, long foodID, String foodName, String foodImage, String categoryImage) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.categoryImage = categoryImage;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getFoodID() {
        return foodID;
    }

    public void setFoodID(long foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "categoryID=" + categoryID +
                ", categoryName='" + categoryName + '\'' +
                ", foodID=" + foodID +
                ", foodName='" + foodName + '\'' +
                ", foodImage='" + foodImage + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                '}';
    }
}
