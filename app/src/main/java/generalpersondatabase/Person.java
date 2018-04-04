package generalpersondatabase;


public class Person {
    private  String age, gender, weight, height, targetWeight, BMRWithActivity, BMRWithoutActivity;
    private long activityLevel;
    private  long personID;
    public Person() {}

    public Person(long personID, String age, String gender, String height, String weight, long activityLevel, String targetWeight,
                  String BMRWithoutActivity, String BMRWithActivity) {
        this.personID = personID;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.targetWeight = targetWeight;
        this.BMRWithoutActivity = BMRWithoutActivity;
        this.BMRWithActivity = BMRWithActivity;
    }

    public String getBMRWithActivity() {
        return BMRWithActivity;
    }

    public void setBMRWithActivity(String BMRWithActivity) {
        this.BMRWithActivity = BMRWithActivity;
    }

    public String getBMRWithoutActivity() {
        return BMRWithoutActivity;
    }

    public void setBMRWithoutActivity(String BMRWithoutActivity) {
        this.BMRWithoutActivity = BMRWithoutActivity;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public long getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(long activityLevel) {
        this.activityLevel = activityLevel;
    }

    public long getPersonID() {
        return personID;
    }

    public void setPersonID(long personID) {
        this.personID = personID;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", targetWeight='" + targetWeight + '\'' +
                ", BMRWithActivity='" + BMRWithActivity + '\'' +
                ", BMRWithoutActivity='" + BMRWithoutActivity + '\'' +
                ", activityLevel=" + activityLevel +
                ", personID=" + personID +
                '}';
    }
}
