package userprofile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.PieChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import generalpersonactivities.BMICalculation;
import generalpersonactivities.ShowBMRActivity;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import trackingdatabase.TrackingOperations;

public class ShowGoalActivity extends AppCompatActivity {
    private PieChart pieChart;
    private Typeface mTfRegular, mTfLight, mTfBold;
    private TextView goalUpdateText, goalNutrientNeedText, goalCalorieNeedText, goalCalNumberText;
    private Button getStarted;
    private double updatedWeight, currentWeight;
    private PersonOperations personData;
    private TrackingOperations trackingData;
    private double newBMRWithActivity, newBMRWithoutActivity;
    private double BMRHeight;
    private Person person, newPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_goal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Bundle bundle = getIntent().getExtras();
        updatedWeight = bundle.getDouble("goalweight");
        currentWeight = bundle.getDouble("currentweight");

        pieChart = findViewById(R.id.pie_chart_goal);
        goalUpdateText = findViewById(R.id.goal_update_text);
        goalNutrientNeedText = findViewById(R.id.goal_calorie_need);
        goalCalorieNeedText = findViewById(R.id.goal_calorie_text);
        goalCalNumberText = findViewById(R.id.goal_calorie_num);
        getStarted = findViewById(R.id.btn_get_started);

        personData = new PersonOperations(this);
        trackingData = new TrackingOperations(this);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mTfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        goalUpdateText.setTypeface(mTfRegular);
        goalUpdateText.setTextSize(30);

        goalNutrientNeedText.setTypeface(mTfLight);
        goalNutrientNeedText.setTextSize(15);

        goalCalorieNeedText.setTypeface(mTfBold);
        goalCalorieNeedText.setTextSize(20);

        goalCalNumberText.setTypeface(mTfBold);
        goalCalNumberText.setTextSize(35);
        goalCalNumberText.setText("2680 CAL");      ///////////////////////Need to be changed


        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawCenterText(false);
        pieChart.setDrawHoleEnabled(false);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.setDrawEntryLabels(false);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(20f, "Protein: 20%"));
        yValues.add(new PieEntry(50f, "Fat: 50%"));
        yValues.add(new PieEntry(30f, "Carbs: 30%"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(Color.BLACK, Color.GRAY, Color.LTGRAY);

        PieData data = new PieData(dataSet);
        dataSet.setLabel(" ");
        dataSet.setValueTextSize(0f);
        data.setValueTextColor(Color.BLACK);


        pieChart.setData(data);

        personData.open();
        person = personData.getPerson(personData.getRowCount());

        personData.close();
        newPerson = new Person();

        BMRHeight = Double.parseDouble(person.getHeight());
        BMRHeight *= 2.54;
        newBMRWithoutActivity = BMICalculation.BMRWithoutActivity(String.valueOf(BMRHeight), String.valueOf(updatedWeight), person.getAge(), person.getGender());
        newBMRWithActivity = BMICalculation.BMRWithActivity(String.valueOf(BMRHeight), String.valueOf(updatedWeight), person.getAge(), person.getGender(), person.getActivityLevel());


        goalCalNumberText.setText(newBMRWithActivity + " CAL");


        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                personData.open();

                personData.deleteAllPersonData();

                newPerson.setAge(person.getAge());
                newPerson.setGender(person.getGender());
                newPerson.setHeight(person.getHeight());
                newPerson.setWeight(String.valueOf(currentWeight));
                newPerson.setActivityLevel(person.getActivityLevel());
                newPerson.setTargetWeight(String.valueOf(updatedWeight));
                newPerson.setBMRWithoutActivity(String.valueOf(newBMRWithoutActivity));
                newPerson.setBMRWithActivity(String.valueOf(newBMRWithActivity));
                newPerson.setWeightUpdateAmount("0");
                newPerson.setWeightUpdateDate(current_date_str);

                personData.addPerson(newPerson);

                personData.close();

                Intent intent = new Intent(ShowGoalActivity.this, ShowBMRActivity.class);
                intent.putExtra("withoutactivity", newBMRWithoutActivity);
                intent.putExtra("withactivity", newBMRWithActivity);
                startActivity(intent);
                finish();
            }
        });
    }
}
