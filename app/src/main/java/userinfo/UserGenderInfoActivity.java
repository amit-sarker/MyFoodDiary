package userinfo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.moumita.caloriecountergeb.R;

import generalpersondatabase.PersonHelper;

public class UserGenderInfoActivity extends AppCompatActivity {

    private boolean isFemale;
    private TextView mGenderInfoText;
    private ImageView mGenderInfoImg;
    private Button mNextPageBtn;
    private Typeface mTfRegular, mtfBold;
    private RadioButton maleBtn, femaleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gender_info);

        PersonHelper userHelper = new PersonHelper();

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mtfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        mGenderInfoText = findViewById(R.id.gender_info_text);
        mGenderInfoImg = findViewById(R.id.gender_info_img);
        mNextPageBtn = findViewById(R.id.next_page_btn);
        maleBtn = findViewById(R.id.male_btn);
        femaleBtn = findViewById(R.id.female_btn);

        mGenderInfoText.setTypeface(mTfRegular);
        maleBtn.setTypeface(mTfRegular);
        femaleBtn.setTypeface(mTfRegular);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.female_btn) {
                    isFemale = true;
                    Intent intent = new Intent(UserGenderInfoActivity.this, UserHeightInfoActivity.class);
                    intent.putExtra("isfemale", isFemale);
                    startActivity(intent);
                    finish();
                } else if (checkedId == R.id.male_btn) {
                    isFemale = false;
                    Intent intent = new Intent(UserGenderInfoActivity.this, UserHeightInfoActivity.class);
                    intent.putExtra("isfemale", isFemale);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
