package generalpersonactivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;
import userinfo.UserSignInActivity;

import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;

import static java.lang.Math.abs;

public class BMICalculationActivity extends AppCompatActivity {

    private PersonOperations personData;
    private Person currentPerson;
    private TextView mBMIText, mCalculatedBMI, mBMIState;
    private double neededWeight, calculatedBMI;
    private Button mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculation);

        mBMIText = findViewById(R.id.bmitext);
        mCalculatedBMI = findViewById(R.id.calculatedbmitext);
        mBMIState = findViewById(R.id.bmitextstate);
        mNextButton = findViewById(R.id.next_page_btn);

        currentPerson = new Person();
        personData = new PersonOperations(this);
        personData.open();

        long last_row_person = personData.getRowCount();
        currentPerson = personData.getPerson(last_row_person);

        BMICalculation bmi = new BMICalculation();
        calculatedBMI = bmi.BMI(currentPerson.getHeight(), currentPerson.getWeight());
        neededWeight =  bmi.WeightState(currentPerson.getHeight(), currentPerson.getWeight());

        String personWeight = currentPerson.getWeight();
        Double currentPersonWeight = Double.parseDouble(personWeight);
        currentPersonWeight = BMICalculation.Round(currentPersonWeight, 2);


        String personBMIState = bmi.BMIState(calculatedBMI);
        mCalculatedBMI.setText(Double.toString(calculatedBMI));
        mCalculatedBMI.setTextColor(Color.parseColor("#795548"));

        if(personBMIState.equals("underweight")) {
            mBMIState.setText("You are Underweight, you need to gain atleast " + abs(neededWeight) + "kg weight to become healthy");
            mBMIState.setTextColor(Color.parseColor("#F44336"));
        } else if(personBMIState.equals("healthy")) {
            if(neededWeight < 0.0) mBMIState.setText("You are Healthy, you can lose " + abs(neededWeight) + "kg weight to maintain standard weight");
            else if(neededWeight > 0.0) mBMIState.setText("You are Healthy, you can gain " + abs(neededWeight) + "kg weight to maintain standard weight");
            else mBMIState.setText("You are Healthy, your weight is standard & you need to maintain this weight");
            mBMIState.setTextColor(Color.parseColor("#388E3C"));
        } else if(personBMIState.equals("overweight")) {
            mBMIState.setText("You are Overweight, you need to lose atleast " + abs(neededWeight) + "kg weight to become healthy");
            mBMIState.setTextColor(Color.parseColor("#E53935"));
        } else if(personBMIState.equals("obese")) {
            mBMIState.setText("You are Obese, you have to lose " + abs(neededWeight) + "kg immediately");
            mBMIState.setTextColor(Color.parseColor("#D50000"));
        }


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BMICalculationActivity.this, UserSignInActivity.class);
                startActivity(intent);
            }
        });


    }
}
