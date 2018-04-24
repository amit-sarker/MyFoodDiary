package userinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gender_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        PersonHelper userHelper = new PersonHelper();


        mGenderInfoText = findViewById(R.id.gender_info_text);
        mGenderInfoImg = findViewById(R.id.gender_info_img);
        mNextPageBtn = findViewById(R.id.next_page_btn);


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
