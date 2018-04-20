package com.example.moumita.caloriecountergeb;

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

import java.util.ArrayList;
import java.util.List;

import fooddatabase.Food;
import fooddatabase.FoodOperations;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import servingdatabase.FoodServing;
import servingdatabase.ServingOperations;

public class AddFoodToDiaryActivity extends AppCompatActivity {


    //String[] textArray = {"1 plate", "1 cup", "1 table spoon", "1 pinch"};
    private String[] textArray;
    String ppp = "food5";
    private Integer[] imageArray;


    private String foodName, servingAmountString;
    private FoodOperations foodData;
    private ServingOperations servingData;
    private Food selectedFood;
    TextView[] foodNeutrientsText;
    private double[] foodNeutrients;
    private double servingAmount, factor = 2.0;
    private int noOfElement = 4, intservingSize = 1;
    private EditText servingAmountEditText;
    private FloatingActionButton addTrackButton;
    private List<FoodServing> foodServingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);

        Bundle bundle = getIntent().getExtras();
        foodName = bundle.getString("foodname");
        foodData = new FoodOperations(this);
        servingData = new ServingOperations(this);
        foodServingList = new ArrayList<>();
        List<String> servingList = new ArrayList<>();
        List<Integer> imageList = new ArrayList<>();
        //textArray = new String[];
        //imageArray = new Integer[];


        //int x = R.drawable.food5;
        //Integer[] imageArray = {resID, R.drawable.food2,
                //R.drawable.food3, R.drawable.food4};

        foodNeutrientsText = new TextView[4];
        foodNeutrients = new double[4];
        foodNeutrientsText[0] = findViewById(R.id.calorie_val);
        foodNeutrientsText[1] = findViewById(R.id.protein_val);
        foodNeutrientsText[2] = findViewById(R.id.carb_val);
        foodNeutrientsText[3] = findViewById(R.id.cholestorol_val);
        TextView text = (TextView) findViewById(R.id.spinnerTextView);
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImages);
        final Spinner spinnerServingSize = (Spinner) findViewById(R.id.mySpinner);
        servingAmountEditText = findViewById(R.id.serving_amount_Edittext);
        addTrackButton = findViewById(R.id.fab_add_tracking_btn);


        servingData.open();

        foodServingList = servingData.getFoodServingByName(foodName);
        for(FoodServing a: foodServingList) {
            servingList.add(a.getFood_serving_measurement());
            imageList.add(ImageID(a.getServing_image_id()));
        }

        servingData.close();

        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRR      " + foodName);

        for(int i = 0; i < servingList.size(); i++) {
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT    " + servingList.get(i));
        }


        foodData.open();
        selectedFood = foodData.getFoodByName(foodName);
        foodNeutrients[0] = selectedFood.getFood_energy();
        foodNeutrients[1] = selectedFood.getFood_proteins();
        foodNeutrients[2] = selectedFood.getFood_carbohydrates();
        foodNeutrients[3] = selectedFood.getFood_fat();


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
            factor = getFactor(intservingSize, servingAmount);
            for (int i = 0; i < noOfElement; i++) {
                foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
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
                            factor = getFactor(intservingSize, servingAmount);
                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
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
                servingAmountEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

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
                            factor = getFactor(intservingSize, servingAmount);
                            for (int i = 0; i < noOfElement; i++) {
                                foodNeutrientsText[i].setText(String.valueOf(factor * foodNeutrients[i]));
                            }
                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public double getFactor(int intservingSize, double servingAmount) {
        //convert intservingSize to gram and multipy it by serving amount
        //Ex - 1 cup = 2.5 gm. and serving amt = 3
        //return 2.5 * 3
        double factor = 1.0;
        if (intservingSize == 0) {
            factor = 85;
        }

        return factor * servingAmount;
    }

    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(ppp , "drawable", getPackageName());
        return resID;
    }
}
