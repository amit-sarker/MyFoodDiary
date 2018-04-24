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
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import activities.TestTabActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import fooddatabase.Food;
import fooddatabase.FoodOperations;
import fooddiarydatabase.DiaryOperations;
import fooddiarydatabase.FoodDiary;
import fragments.HomeFragment;
import generalpersonactivities.BMICalculation;
import servingdatabase.FoodServing;
import servingdatabase.ServingOperations;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

public class AddFoodToDiaryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String foodName, servingAmountString;
    private FoodOperations foodData;
    private ServingOperations servingData;
    private TrackingOperations trackingData;
    private CalorieTracking trackingRow;
    private Food selectedFood;
    private TextView[] foodNeutrientsText;
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
    private DiaryOperations diaryData;
    private String mealType;
    HomeFragment homeFragment;
    private TextView foodNameText;
    private CircleImageView foodImage;
    private String foodImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Bundle bundle = getIntent().getExtras();
        foodName = bundle.getString("foodname");
        mealType = bundle.getString("meal_type");
        homeFragment = new HomeFragment();

        foodData = new FoodOperations(this);
        servingData = new ServingOperations(this);
        trackingData = new TrackingOperations(this);
        diaryData = new DiaryOperations(this);
        foodServingList = new ArrayList<>();
        servingList = new ArrayList<>();
        imageList = new ArrayList<>();
        gramList = new ArrayList<>();
        trackingRow = new CalorieTracking();
        noOfElement = 9;

        foodNeutrientsText = new TextView[9];
        foodNeutrients = new double[9];
        selected_food = new double[9];
        foodNeutrientsText[0] = findViewById(R.id.calorie_val);
        foodNeutrientsText[1] = findViewById(R.id.protein_val);
        foodNeutrientsText[2] = findViewById(R.id.carb_val);
        foodNeutrientsText[3] = findViewById(R.id.cholestorol_val);
        foodNeutrientsText[4] = findViewById(R.id.dietry_fiber_val);
        foodNeutrientsText[5] = findViewById(R.id.food_water_val);
        foodNeutrientsText[6] = findViewById(R.id.vit_a_val);
        foodNeutrientsText[7] = findViewById(R.id.vit_c_val);
        foodNeutrientsText[8] = findViewById(R.id.vit_e_val);

        TextView text = findViewById(R.id.spinnerTextView);
        ImageView imageView = findViewById(R.id.spinnerImages);
        final Spinner spinnerServingSize = findViewById(R.id.mySpinner);
        servingAmountEditText = findViewById(R.id.serving_amount_Edittext);
        addTrackButton = findViewById(R.id.fab_add_tracking_btn);
        foodNameText = findViewById(R.id.food_name_text);
        foodImage = findViewById(R.id.food_circleimage);


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
        selected_food[4] = selectedFood.getFood_fiber();
        selected_food[5] = selectedFood.getFood_water();
        selected_food[6] = selectedFood.getFood_vit_a();
        selected_food[7] = selectedFood.getFood_vit_c();
        selected_food[8] = selectedFood.getFood_vit_e();
        foodImageId = selectedFood.getFood_image();

        foodData.close();

        foodNameText.setText(foodName);
        foodImage.setImageResource(ImageID(foodImageId));

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


                CalorieTracking newTrackingData = new CalorieTracking();
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

                diaryData.open();

                FoodDiary newDiary = new FoodDiary();
                newDiary.setFood_name(foodName);
                newDiary.setFood_serving_measurement(servingList.get(intservingSize));
                newDiary.setFood_serving_amount(servingAmountString);
                newDiary.setMeal_type(mealType); //Should be editedddddddddddddddddddddddddddddddddddddd
                newDiary.setDate(trackingRow.getDate());
                newDiary.setTotal_cal_selected_food(String.valueOf(foodNeutrients[0]));
                Toast.makeText(getApplicationContext(), "Diary Added", Toast.LENGTH_SHORT).show();

                diaryData.addFoodDiary(newDiary);
                diaryData.close();

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
