package generalpersonactivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
    private TextView bmrHeaderView, bmrDataview, congratsText, welcomeText, welcome2Text;
    private String bmrText;
    private Button nextButton;
    private CalorieTracking calorieTrackingData;
    private TrackingOperations trackingData;
    private Typeface mTfRegular, mtfBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bmr);

        congratsText = findViewById(R.id.congrats_text);
        welcomeText = findViewById(R.id.welcome_text);
        bmrHeaderView = findViewById(R.id.bmr_text);
        bmrDataview = findViewById(R.id.bmr_text_data);
        nextButton = findViewById(R.id.next_button);
        welcome2Text = findViewById(R.id.welcome_text2);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mtfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        congratsText.setTypeface(mTfRegular);
        welcomeText.setTypeface(mTfRegular);
        bmrHeaderView.setTypeface(mTfRegular);
        bmrDataview.setTypeface(mTfRegular);
        welcome2Text.setTypeface(mTfRegular);
        nextButton.setTypeface(mTfRegular);

        trackingData = new TrackingOperations(this);

        Bundle bundle = getIntent().getExtras();
        BMRWithoutActivity = bundle.getDouble("withoutactivity");
        BMRWithActivity = bundle.getDouble("withactivity");

        bmrText = "";
        bmrText += "Without Activity: " + BMRWithoutActivity + "\n" + "With Activity: " +
                BMRWithActivity;
        bmrDataview.setText(bmrText);


        proteinsWithActivity = Math.round(BMRWithActivity * 0.25);
        fatWithActivity = Math.round(BMRWithActivity * 0.25);
        carbsWithActivity = Math.round(BMRWithActivity * 0.5);

        trackingData.open();

        CalorieTracking lastTrackingRow = new CalorieTracking();
        if(trackingData.getRowCount() != 0) {
            lastTrackingRow = trackingData.getTracking(trackingData.getRowCount());
        }

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

        if(trackingData.getRowCount() != 0) {
            calorieTrackingData.setWater_consumed(lastTrackingRow.getWater_consumed());
            calorieTrackingData.setGoal_point(lastTrackingRow.getGoal_point());
            calorieTrackingData.setRank(lastTrackingRow.getRank());
        } else {
            calorieTrackingData.setWater_consumed(0.0);
            calorieTrackingData.setGoal_point(0);
            calorieTrackingData.setRank(0);
        }


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
