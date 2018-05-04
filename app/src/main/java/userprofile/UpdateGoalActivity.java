package userprofile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class UpdateGoalActivity extends AppCompatActivity {
    private TextView goalWeightText, goalWeightMotivationText;
    private EditText inputGoalWeight;
    private FancyButton kg2lbsBtn;
    private Button continueButton;
    private Typeface mTfRegular, mTfLight, mTfBold;
    private Vibrator vibe;
    private double currentWeight, goalWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goal);

        Bundle bundle = getIntent().getExtras();
        currentWeight = bundle.getDouble("currentweight");

        goalWeightText = findViewById(R.id.goal_weight_text_view);
        goalWeightMotivationText = findViewById(R.id.goal_weight_motivation_text);
        inputGoalWeight = findViewById(R.id.input_goal_weight);
        kg2lbsBtn = findViewById(R.id.btn_kg_lbs);
        continueButton = findViewById(R.id.btn_signup);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mTfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        goalWeightText.setTypeface(mTfRegular);
        goalWeightText.setTextSize(30);

        goalWeightMotivationText.setTypeface(mTfLight);
        goalWeightMotivationText.setTextSize(15);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(25);

                final String goal_weight = inputGoalWeight.getText().toString();

                if(goal_weight.isEmpty()) {
                    inputGoalWeight.setError("enter your goal weight");
                    return;
                } else {
                    inputGoalWeight.setError(null);
                }

                goalWeight = Double.parseDouble(goal_weight);

                Intent intent = new Intent(UpdateGoalActivity.this, ShowGoalActivity.class);
                intent.putExtra("currentweight", currentWeight);
                intent.putExtra("goalweight", goalWeight);
                startActivity(intent);
                finish();
            }
        });
    }
}
