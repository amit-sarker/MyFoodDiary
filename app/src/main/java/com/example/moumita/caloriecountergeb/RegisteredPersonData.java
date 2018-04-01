package com.example.moumita.caloriecountergeb;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;

public class RegisteredPersonData extends AppCompatActivity {

    private Person registeredPerson;
    private PersonOperations personData;
    private String registeredPersonAge, registeredPersonGender, registeredPersonHeight, registeredPersonWeight;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private TextView infoHeadingText, userInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_person_data);

        infoHeadingText = findViewById(R.id.user_info_headline_text);
        userInfoText = findViewById(R.id.info_text);
        registeredPerson = new Person();
        personData = new PersonOperations(this);
        personData.open();

        registeredPerson = personData.getPerson(1);
        registeredPersonAge = registeredPerson.getAge();
        registeredPersonGender = registeredPerson.getGender();
        registeredPersonHeight = registeredPerson.getHeight();
        registeredPersonWeight = registeredPerson.getWeight();
        mAuth = FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid().toString();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        Map userInfo = new HashMap();
        userInfo.put("age", registeredPersonAge);
        userInfo.put("gender", registeredPersonGender);
        userInfo.put("height", registeredPersonHeight);
        userInfo.put("weight", registeredPersonWeight);
        mUserRef.setValue(userInfo);


        String infoTextUI = "Age: " + registeredPersonAge + "\n" + "Gender: " + registeredPersonGender
                + "\n" + "Height: " + registeredPersonHeight + "\n" + "Weight: " + registeredPersonWeight;
        userInfoText.setText(infoTextUI);
        userInfoText.setTextColor(Color.parseColor("#795548"));
    }
}
