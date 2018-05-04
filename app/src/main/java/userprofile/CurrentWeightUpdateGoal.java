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

public class CurrentWeightUpdateGoal extends AppCompatActivity {
    private TextView currentWeightText, currentWeightMotivationText;
    private EditText inputCurrentWeight;
    private FancyButton kg2lbsBtn;
    private Button continueButton;
    private Typeface mTfRegular, mTfLight, mTfBold;
    private Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weight_update_goal);

        currentWeightText = findViewById(R.id.current_weight_text_view);
        currentWeightMotivationText = findViewById(R.id.current_weight_motivation_text);
        inputCurrentWeight = findViewById(R.id.input_current_weight);
        kg2lbsBtn = findViewById(R.id.btn_kg_lbs_current);
        continueButton = findViewById(R.id.btn_cont);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mTfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        currentWeightText.setTypeface(mTfRegular);
        currentWeightText.setTextSize(30);

        currentWeightMotivationText.setTypeface(mTfLight);
        currentWeightMotivationText.setTextSize(15);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(25);
                final String current_weight = inputCurrentWeight.getText().toString();

                if(current_weight.isEmpty()) {
                    inputCurrentWeight.setError("enter you current weight");
                    return;
                } else {
                    inputCurrentWeight.setError(null);
                }

                double current_weight_double = Double.parseDouble(current_weight);

                Intent intent = new Intent(CurrentWeightUpdateGoal.this, UpdateGoalActivity.class);
                intent.putExtra("currentweight", current_weight_double);
                startActivity(intent);
                finish();
            }
        });
    }
}
