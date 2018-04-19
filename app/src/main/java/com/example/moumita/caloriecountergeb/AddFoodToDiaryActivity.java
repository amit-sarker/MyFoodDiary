package com.example.moumita.caloriecountergeb;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import fooddatabase.Food;
import fooddatabase.FoodOperations;

public class AddFoodToDiaryActivity extends AppCompatActivity {


    String[] textArray = { "1 plate", "1 cup", "1 table spoon", "1 pinch" };
    Integer[] imageArray = { R.drawable.food5, R.drawable.food2,
            R.drawable.food3, R.drawable.food4 };

    private String foodName, servingAmountString;
    private FoodOperations foodData;
    private Food selectedFood;
    TextView[] foodNeutrientsText;
    private double[] foodNeutrients;
    private double servingAmount, factor = 2.0;
    private int noOfElement = 4, intservingSize = 1;
    private EditText servingAmountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);

        Bundle bundle = getIntent().getExtras();
        foodName = bundle.getString("foodname");
        foodData = new FoodOperations(this);

        foodNeutrientsText = new TextView[4];
        foodNeutrients = new double[4];
        foodNeutrientsText[0] = findViewById(R.id.calorie_val);
        foodNeutrientsText[1] = findViewById(R.id.protein_val);
        foodNeutrientsText[2] = findViewById(R.id.carb_val);
        foodNeutrientsText[3] = findViewById(R.id.cholestorol_val);
        TextView text = (TextView) findViewById(R.id.spinnerTextView);
        ImageView imageView =(ImageView)findViewById(R.id.spinnerImages);
        final Spinner spinnerServingSize = (Spinner) findViewById(R.id.mySpinner);
        servingAmountEditText = findViewById(R.id.serving_amount_Edittext);


        foodData.open();
        selectedFood = foodData.getFoodByName(foodName);
        foodNeutrients[0] = selectedFood.getFood_energy();
        foodNeutrients[1] = selectedFood.getFood_proteins();
        foodNeutrients[2] = selectedFood.getFood_carbohydrates();
        foodNeutrients[3] = selectedFood.getFood_fat();




        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, textArray, imageArray);
        spinnerServingSize.setAdapter(adapter);
        intservingSize = spinnerServingSize.getSelectedItemPosition();
        servingAmountString = servingAmountEditText.getText().toString();

        if(servingAmountString.matches(""))
        {
            factor = 0.0;
            for(int i=0;i<noOfElement;i++)
            {
                foodNeutrientsText[i].setText(String.valueOf(factor*foodNeutrients[i]));
            }
        }
        else
        {
            servingAmount = Double.parseDouble(servingAmountString);
            factor = getFactor(intservingSize, servingAmount);
            for(int i=0;i<noOfElement;i++)
            {
                foodNeutrientsText[i].setText(String.valueOf(factor*foodNeutrients[i]));
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
                        if(servingAmountString.matches(""))
                        {
                            factor = 0.0;
                            for(int i=0;i<noOfElement;i++)
                            {
                                foodNeutrientsText[i].setText(String.valueOf(factor*foodNeutrients[i]));
                            }
                        }
                        else
                        {
                            servingAmount = Double.parseDouble(servingAmountString);
                            factor = getFactor(intservingSize, servingAmount);
                            for(int i=0;i<noOfElement;i++)
                            {
                                foodNeutrientsText[i].setText(String.valueOf(factor*foodNeutrients[i]));
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
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        servingAmountString = servingAmountEditText.getText().toString();
                        if(servingAmountString.matches(""))
                        {
                            factor = 0.0;
                            for(int i=0;i<noOfElement;i++)
                            {
                                foodNeutrientsText[i].setText(String.valueOf(factor*foodNeutrients[i]));
                            }
                        }
                        else
                        {
                            servingAmount = Double.parseDouble(servingAmountString);
                            factor = getFactor(intservingSize, servingAmount);
                            for(int i=0;i<noOfElement;i++)
                            {
                                foodNeutrientsText[i].setText(String.valueOf(factor*foodNeutrients[i]));
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
        if(intservingSize==0)
        {
            factor = 85;
        }

        return factor * servingAmount;
    }
}
