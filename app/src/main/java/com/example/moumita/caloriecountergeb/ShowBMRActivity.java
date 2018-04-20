package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import activities.TestTabActivity;

public class ShowBMRActivity extends AppCompatActivity {

    private  double BMRWithoutActivity, BMRWithActivity;
    private TextView bmrHeaderView, bmrDataview;
    private String bmrText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bmr);

        bmrHeaderView = findViewById(R.id.bmr_text);
        bmrDataview = findViewById(R.id.bmr_text_data);
        nextButton = findViewById(R.id.next_button);

        Bundle bundle = getIntent().getExtras();
        BMRWithoutActivity = bundle.getDouble("withoutactivity");
        BMRWithActivity = bundle.getDouble("withactivity");

        bmrText = "";
        bmrText += "Without Activity: " + BMRWithoutActivity + "\n" + "With Activity: " +
                BMRWithActivity;
        bmrDataview.setText(bmrText);
        bmrDataview.setTextColor(Color.parseColor("#388E3C"));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowBMRActivity.this, TestTabActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
