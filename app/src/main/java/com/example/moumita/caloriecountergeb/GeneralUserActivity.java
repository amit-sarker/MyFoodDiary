package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import generalpersonactivities.BMICalculationActivity;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;

public class GeneralUserActivity extends AppCompatActivity {

    private boolean isFemale;
    private double height, weight;
    private int age;

    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button addUpdateButton;
    private Person newPerson;
    private Person oldPerson;
    private String mode;
    private long personId;
    private PersonOperations personData;



    private EditText mGeneralUserAgeText;
    private EditText mGeneralUserGenderText;
    private EditText mGeneralUserWeightText;
    private EditText mGeneralUserHeightText;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_user);

        //Getting Values from the previous activity
        Bundle bundle = getIntent().getExtras();
        isFemale = bundle.getBoolean("isfemale");
        //isfeet = bundle.getBoolean("isfeet");
        height = bundle.getDouble("height");
        age = bundle.getInt("age");
        //iskg = bundle.getBoolean("iskg");
        weight = bundle.getDouble("weight");

        newPerson = new Person();
        oldPerson = new Person();
        mGeneralUserAgeText = findViewById(R.id.age_text_general);
        mGeneralUserGenderText = findViewById(R.id.gender_text_general);
        mGeneralUserWeightText = findViewById(R.id.weight_text_general);
        mGeneralUserHeightText = findViewById(R.id.height_text_general);
        mSubmitButton = findViewById(R.id.submit_btn);
        personData = new PersonOperations(this);
        personData.open();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String generalUserAge = mGeneralUserAgeText.getText().toString();
                final String generalUserGender = mGeneralUserGenderText.getText().toString();
                final String generalUserHeight = mGeneralUserHeightText.getText().toString();
                final String generalUserWeight = mGeneralUserWeightText.getText().toString();

                newPerson.setAge(String.valueOf(age));
                if(isFemale) newPerson.setGender("female");
                else newPerson.setGender("male");
                newPerson.setHeight(String.valueOf(height));
                newPerson.setWeight(String.valueOf(weight));

                personData.addPerson(newPerson);

                Toast t = Toast.makeText(GeneralUserActivity.this, "Person "+ newPerson.getPersonID() + "has been added successfully !", Toast.LENGTH_SHORT);
                t.show();

                //Person test = personData.getPerson(2);
                //System.out.println(test.toString());
                Intent intent = new Intent(GeneralUserActivity.this, BMICalculationActivity.class);
                startActivity(intent);

            }
        });
    }
}
