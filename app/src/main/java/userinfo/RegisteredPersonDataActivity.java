package userinfo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import activities.HomeTabActivity;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;

public class RegisteredPersonDataActivity extends AppCompatActivity {

    private Person registeredPerson;
    private PersonOperations personData;
    private String registeredPersonAge, registeredPersonGender, registeredPersonHeight, registeredPersonWeight,
            registeredPersonTargetWeight, registeredPersonBMRWithoutActivity, registeredPersonBMRWithActivity,
            registeredPersonWeightUpdateAmount, registeredPersonWeightUpdateDate;
    private long registeredPersonActivityLevel;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private TextView infoHeadingText, userInfoText;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_person_data);

        infoHeadingText = findViewById(R.id.user_info_headline_text);
        userInfoText = findViewById(R.id.info_text);
        registeredPerson = new Person();
        personData = new PersonOperations(this);
        homeButton = findViewById(R.id.home_btn);
        personData.open();

        long last_row_person = personData.getRowCount();
        registeredPerson = personData.getPerson(last_row_person);

        personData.close();

        registeredPersonAge = registeredPerson.getAge();
        registeredPersonGender = registeredPerson.getGender();
        registeredPersonHeight = registeredPerson.getHeight();
        registeredPersonWeight = registeredPerson.getWeight();
        registeredPersonActivityLevel = registeredPerson.getActivityLevel();
        registeredPersonTargetWeight = registeredPerson.getTargetWeight();
        registeredPersonBMRWithoutActivity = registeredPerson.getBMRWithoutActivity();
        registeredPersonBMRWithActivity = registeredPerson.getBMRWithActivity();
        registeredPersonWeightUpdateAmount = registeredPerson.getWeightUpdateAmount();
        registeredPersonWeightUpdateDate = registeredPerson.getWeightUpdateDate();

        mAuth = FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid().toString();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        Map userInfo = new HashMap();
        userInfo.put("age", registeredPersonAge);
        userInfo.put("gender", registeredPersonGender);
        userInfo.put("height", registeredPersonHeight);
        userInfo.put("weight", registeredPersonWeight);
        userInfo.put("activitylevel", registeredPersonActivityLevel);
        userInfo.put("targetweight", registeredPersonTargetWeight);
        userInfo.put("bmrwithoutactivity", registeredPersonBMRWithoutActivity);
        userInfo.put("bmrwithactivity", registeredPersonBMRWithActivity);
        userInfo.put("weightupdateamount", registeredPersonWeightUpdateAmount);
        userInfo.put("weightupdatedate", registeredPersonWeightUpdateDate);

        mUserRef.setValue(userInfo);


        String infoTextUI = "Age: " + registeredPersonAge + "\n" + "Gender: " + registeredPersonGender
                + "\n" + "Height: " + registeredPersonHeight + "\n" + "Weight: " + registeredPersonWeight
                + "\n" + "Activity Level: " + registeredPersonActivityLevel + "\n" + "Target Weight: " +
                registeredPersonTargetWeight + "\n" + "BMR (Without Activity): " + registeredPersonBMRWithoutActivity + "\n" +
                "BMR (With Activity): " + registeredPersonBMRWithActivity + "\n" + "Wight Update Amount" + registeredPersonWeightUpdateAmount + "\n" + "Wight Update Date" + registeredPersonWeightUpdateDate;
        userInfoText.setText(infoTextUI);
        userInfoText.setTextColor(Color.parseColor("#795548"));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteredPersonDataActivity.this, HomeTabActivity.class);
                startActivity(intent);
            }
        });
    }
}
