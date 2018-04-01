package generalpersondatabase;


public class Person {
    public  String age, gender, weight, height;
    public  long personID;
    public Person() {}

    public Person(long personID, String age, String gender, String height, String weight) {
        this.personID = personID;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
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
        return "Person id: " + getPersonID()+ "\n" +
                "Age: " + getAge() + "\n" +
                "Gender: " + getGender() + "\n" +
                "Height: " + getHeight() + "\n" +
                "Weight: " + getWeight();
    }
}
