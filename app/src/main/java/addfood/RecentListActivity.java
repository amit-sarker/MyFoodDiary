package addfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.moumita.caloriecountergeb.R;

import fooddiarydatabase.DiaryOperations;
import trackingdatabase.TrackingOperations;

public class RecentListActivity extends AppCompatActivity {

    private ListView recentListView;
    private String mealType;
    private TrackingOperations trackingData;
    private DiaryOperations diaryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_list);

        Bundle bundle = getIntent().getExtras();
        mealType = bundle.getString("meal_type");

        recentListView = findViewById(R.id.recent_list_view);

        diaryData = new DiaryOperations(this);
        diaryData.open();
        diaryData.getAllFoodDiary();

    }
}
