package generalpersonactivities;

public class BMICalculation {
    public BMICalculation() {}

    public double BMI(String height, String weight) {
        double currentHeight, currentWeight;
        currentHeight = Double.parseDouble(height);
        currentWeight = Double.parseDouble(weight);
        currentHeight *= 0.0254;
        double bmi = currentWeight / (currentHeight * currentHeight);
        return Round(bmi, 2);
    }

    public String BMIState(double bmi) {
        if(bmi < 18.5) return "underweight";
        else if(bmi >= 18.5 && bmi <= 24.9) return "healthy";
        else if(bmi >= 25.0 && bmi <= 29.9) return "overweight";
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

    public static double Round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
