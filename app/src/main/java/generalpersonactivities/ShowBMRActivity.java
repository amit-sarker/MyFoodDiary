package generalpersonactivities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import activities.HomeTabActivity;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

public class ShowBMRActivity extends AppCompatActivity {

    private  double BMRWithoutActivity, BMRWithActivity, proteinsWithActivity, fatWithActivity, carbsWithActivity;
    private TextView bmrHeaderView, bmrDataview;
    private String bmrText;
    private Button nextButton;
    private CalorieTracking calorieTrackingData;
    private TrackingOperations trackingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bmr);

        bmrHeaderView = findViewById(R.id.bmr_text);
        bmrDataview = findViewById(R.id.bmr_text_data);
        nextButton = findViewById(R.id.next_button);

        trackingData = new TrackingOperations(this);

        Bundle bundle = getIntent().getExtras();
        BMRWithoutActivity = bundle.getDouble("withoutactivity");
        BMRWithActivity = bundle.getDouble("withactivity");

        bmrText = "";
        bmrText += "Without Activity: " + BMRWithoutActivity + "\n" + "With Activity: " +
                BMRWithActivity;
        bmrDataview.setText(bmrText);
        bmrDataview.setTextColor(Color.parseColor("#388E3C"));

        proteinsWithActivity = Math.round(BMRWithActivity * 0.25);
        fatWithActivity = Math.round(BMRWithActivity * 0.25);
        carbsWithActivity = Math.round(BMRWithActivity * 0.5);

        calorieTrackingData = new CalorieTracking();
        String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        calorieTrackingData.setDate(current_date_str);

        calorieTrackingData.setCal_needed(BMRWithActivity);
        calorieTrackingData.setCal_consumed(0.0);
        calorieTrackingData.setCal_remaining(BMRWithActivity);

        calorieTrackingData.setProtein_needed(Math.round(proteinsWithActivity / 4.0));
        calorieTrackingData.setProtein_consumed(0.0);
        calorieTrackingData.setProtein_remaining(Math.round(proteinsWithActivity / 4.0));

        calorieTrackingData.setFat_needed(Math.round(fatWithActivity / 9.0));
        calorieTrackingData.setFat_consumed(0.0);
        calorieTrackingData.setFat_remaining(Math.round(fatWithActivity / 9.0));

        calorieTrackingData.setCarbs_needed(Math.round(carbsWithActivity / 4.0));
        calorieTrackingData.setCarbs_consumed(0.0);
        calorieTrackingData.setCarbs_remaining(Math.round(carbsWithActivity / 4.0));

        trackingData.open();
        trackingData.addTrackingData(calorieTrackingData);
        trackingData.close();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowBMRActivity.this, HomeTabActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
