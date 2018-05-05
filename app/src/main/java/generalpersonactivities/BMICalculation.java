package generalpersonactivities;

public class BMICalculation {
    public BMICalculation() {
    }

    public double BMI(String height, String weight) {
        double currentHeight, currentWeight;
        currentHeight = Double.parseDouble(height);
        currentWeight = Double.parseDouble(weight);
        currentHeight *= 0.0254;
        double bmi = currentWeight / (currentHeight * currentHeight);
        return Round(bmi, 2);
    }

    public String BMIState(double bmi) {
        if (bmi < 18.5) return "underweight";
        else if (bmi >= 18.5 && bmi <= 24.9) return "healthy";
        else if (bmi >= 25.0 && bmi <= 29.9) return "overweight";
        else return "obese";
    }

    public double WeightState(String height, String weight) {
        double currentHeight, currentWeight, neededWeight;
        currentHeight = Double.parseDouble(height);
        currentWeight = Double.parseDouble(weight);
        currentHeight *= 0.0254;
        neededWeight = ((currentHeight * currentHeight) * 21.7) - currentWeight;

        return Round(neededWeight, 2);
    }


    //BMR, the daily needed calorie to fullfill the goal --> height in centimeter, weight in kg, age in year
    public static double BMRWithoutActivity(String height, String targetWeight, String age, String gender) {
        double BMR = 0.0;
        double doubleHeightCM, doubleTargetWeight, doubleAge;
        doubleHeightCM = Double.parseDouble(height);
        doubleTargetWeight = Double.parseDouble(targetWeight);
        doubleAge = Double.parseDouble(age);
        if (gender.startsWith("m")) {
            BMR = 66.5 + (13.75 * doubleTargetWeight) + (5.003 * doubleHeightCM) - (6.755 * doubleAge);
        } else {
            BMR = 655.1 + (9.563 * doubleTargetWeight) + (1.850 * doubleHeightCM) - (4.676 * doubleAge);
        }

        BMR = Math.round(BMR);
        return BMR;
    }

    public static double BMRWithActivity(String height, String targetWeight, String age, String gender, long activityLevel) {
        double BMR = 0.0;
        double doubleHeightCM, doubleTargetWeight, doubleAge;
        doubleHeightCM = Double.parseDouble(height);
        doubleTargetWeight = Double.parseDouble(targetWeight);
        doubleAge = Double.parseDouble(age);
        if (gender.startsWith("m")) {
            BMR = 66.5 + (13.75 * doubleTargetWeight) + (5.003 * doubleHeightCM) - (6.755 * doubleAge);
        } else {
            BMR = 655.1 + (9.563 * doubleTargetWeight) + (1.850 * doubleHeightCM) - (4.676 * doubleAge);
        }

        BMR = Math.round(BMR);

        if (activityLevel == 0) BMR *= 1.2; //Sedentary, Little or no Exercise/ desk job
        else if (activityLevel == 1)
            BMR *= 1.375; //Lightly active, Light exercise/ sports 1 – 3 days/ week
        else if (activityLevel == 2)
            BMR *= 1.55; //Moderately active, Moderate Exercise, sports 3 – 5 days/ week
        else if (activityLevel == 3)
            BMR *= 1.725; //Very active. Heavy Exercise/ sports 6 – 7 days/ week
        else if (activityLevel == 4)
            BMR *= 1.9; //Extremely active, Very heavy exercise/ physical job/ training 2 x/ day

        BMR = Math.round(BMR);

        return BMR;
    }

    public static double Round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
