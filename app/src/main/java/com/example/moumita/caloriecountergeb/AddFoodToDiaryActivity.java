package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import activities.TestTabActivity;
import fooddatabase.Food;
import fooddatabase.FoodOperations;
import generalpersonactivities.BMICalculation;
import servingdatabase.FoodServing;
import servingdatabase.ServingOperations;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

public class AddFoodToDiaryActivity extends AppCompatActivity {

    private String foodName, servingAmountString;
    private FoodOperations foodData;
    private ServingOperations servingData;
    private TrackingOperations trackingData;
    private CalorieTracking trackingRow;
    private Food selectedFood;
    TextView[] foodNeutrientsText;
    private double[] foodNeutrients;
    private double[] selected_food;
    private double servingAmount, factor;
    private int noOfElement, intservingSize;
    private EditText servingAmountEditText;
    private FloatingActionButton addTrackButton;
    private List<FoodServing> foodServingList;
    private List<Double> gramList;
    private List<String> servingList;
    private List<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);

        Bundle bundle = getIntent().getExtras();
        foodName = bundle.getString("foodname");
        foodData = new FoodOperations(this);
        servingData = new ServingOperations(this);
        trackingData = new TrackingOperations(this);
        foodServingList = new ArrayList<>();
        servingList = new ArrayList<>();
        imageList = new ArrayList<>();
        gramList = new ArrayList<>();
        trackingRow = new CalorieTracking();
        noOfElement = 4;

        foodNeutrientsText = new TextView[4];
        foodNeutrients = new double[4];
        selected_food = new double[4];
        foodNeutrientsText[0] = findViewById(R.id.calorie_val);
        foodNeutrientsText[1] = findViewById(R.id.protein_val);
        foodNeutrientsText[2] = findViewById(R.id.carb_val);
        foodNeutrientsText[3] = findViewById(R.id.cholestorol_val);
        TextView text = findViewById(R.id.spinnerTextView);
        ImageView imageView = findViewById(R.id.spinnerImages);
        final Spinner spinnerServingSize = findViewById(R.id.mySpinner);
        servingAmountEditText = findViewById(R.id.serving_amount_Edittext);
        addTrackButton = findViewById(R.id.fab_add_tracking_btn);


        servingData.open();

        foodServingList = servingData.getFoodServingByName(foodName);
        for (FoodServing a : foodServingList) {
            servingList.add(a.getFood_serving_measurement());
            imageList.add(ImageID(a.getServing_image_id()));
            gramList.add(Double.valueOf(a.getServing_size_to_grams()));
        }

        servingData.close();

        foodData.open();
        selectedFood = foodData.getFoodByName(foodName);
        selected_food[0] = selectedFood.getFood_energy();
        selected_food[1] = selectedFood.getFood_proteins();
        selected_food[2] = selectedFood.getFood_carbohydrates();
        selected_food[3] = selectedFood.getFood_fat();

        foodData.close();

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, servingList, imageList);
        spinnerServingSize.setAdapter(adapter);
        intservingSize = spinnerServingSize.getSelectedItemPosition();
        servingAmountString = servingAmountEditText.getText().toString();

        if (servingAmountString.matches("")) {
            factor = 0.0;
            for (int i = 0; i < noOfElement; i++) {
                foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
            }
        } else {
            servingAmount = Double.parseDouble(servingAmountString);
            for (int i = 0; i < noOfElement; i++) {
                foodNeutrients[i] = getCalculatedNutrients(servingAmount, gramList.get(intservingSize), selected_food[i]);
            }

            for (int i = 0; i < noOfElement; i++) {
                foodNeutrientsText[i].setText(String.valueOf(foodNeutrients[i]));
            }
        }

        servingAmountEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                spinnerServingSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        intservingSize = position;
                        servingAmountString = servingAmountEditText.getText().toString();
                        if (servingAmountString.matches("")) {
                            factor = 0.0;
                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
                            }
                        } else {
                            servingAmount = Double.parseDouble(servingAmountString);
                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrients[i] = getCalculatedNutrients(servingAmount, gramList.get(intservingSize), selected_food[i]);
                            }

                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrientsText[i].setText(String.valueOf(foodNeutrients[i]));
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        });


        spinnerServingSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intservingSize = position;
                servingAmountString = servingAmountEditText.getText().toString();
                if (servingAmountString.matches("")) {
                    factor = 0.0;
                    for (int i = 0; i < noOfElement; i++) {
                        foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
                    }
                } else {
                    servingAmount = Double.parseDouble(servingAmountString);
                    for (int i = 0; i < noOfElement; i++) {
                        foodNeutrients[i] = getCalculatedNutrients(servingAmount, gramList.get(intservingSize), selected_food[i]);
                    }

                    for (int i = 0; i < noOfElement; i++) {
                        foodNeutrientsText[i].setText(String.valueOf(foodNeutrients[i]));
                    }
                }
                servingAmountEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        servingAmountString = servingAmountEditText.getText().toString();
                        if (servingAmountString.matches("")) {
                            factor = 0.0;
                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
                            }
                        } else {
                            servingAmount = Double.parseDouble(servingAmountString);
                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrients[i] = getCalculatedNutrients(servingAmount, gramList.get(intservingSize), selected_food[i]);
                            }

                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrientsText[i].setText(String.valueOf(foodNeutrients[i]));
                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                trackingData.open();
                long last_row = trackingData.getRowCount();
                trackingRow = trackingData.getTracking(last_row);

                //String current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                //String tracking_row_date = trackingRow.getDate();
                final CalorieTracking newTrackingData = new CalorieTracking();
                newTrackingData.setCalorie_tracking_id(trackingRow.getCalorie_tracking_id());
                newTrackingData.setDate(trackingRow.getDate());
                newTrackingData.setCal_needed(trackingRow.getCal_needed());
                newTrackingData.setCal_consumed(Math.round(trackingRow.getCal_consumed() + foodNeutrients[0]));
                newTrackingData.setCal_remaining(Math.round(trackingRow.getCal_remaining() - foodNeutrients[0]));
                newTrackingData.setProtein_needed(trackingRow.getProtein_needed());
                newTrackingData.setProtein_consumed(Math.round(trackingRow.getProtein_consumed() + foodNeutrients[1]));
                newTrackingData.setProtein_remaining(Math.round(trackingRow.getProtein_remaining() - foodNeutrients[1]));
                newTrackingData.setFat_needed(trackingRow.getFat_needed());
                newTrackingData.setFat_consumed(Math.round(trackingRow.getFat_consumed() + foodNeutrients[3]));
                newTrackingData.setFat_remaining(Math.round(trackingRow.getFat_remaining() - foodNeutrients[3]));
                newTrackingData.setCarbs_needed(trackingRow.getCarbs_needed());
                newTrackingData.setCarbs_consumed(Math.round(trackingRow.getCarbs_consumed() + foodNeutrients[2]));
                newTrackingData.setCarbs_remaining(Math.round(trackingRow.getCarbs_remaining() - foodNeutrients[2]));

                trackingData.updateTracking(newTrackingData);
                trackingData.close();
                Intent intent = new Intent(AddFoodToDiaryActivity.this, TestTabActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public double getCalculatedNutrients(double servingAmount, double grams, double in_100_grams) {
        grams = servingAmount * grams;
        return BMICalculation.Round((in_100_grams * grams) / 100.0, 2);
    }

    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getPackageName());
        return resID;
    }
}
