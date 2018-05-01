package addfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.moumita.caloriecountergeb.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FoodListAdapter;
import categorydatabase.CategoryOperations;
import categorydatabase.FoodCategory;
import fooddatabase.Food;
import fooddatabase.FoodOperations;
import fooddiarydatabase.DiaryOperations;
import fooddiarydatabase.FoodDiary;
import helper.FoodListHelper;
import helper.ShowFood;

public class RecentListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DiaryOperations diaryData;
    private FoodOperations foodData;
    //private List<String> categoryList = new ArrayList<>();
    private String mealType;

    private ListView recentListView;
    private FoodListAdapter foodListAdapter;
    private List<FoodListHelper> recentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_list);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        foodData = new FoodOperations(this);
        diaryData = new DiaryOperations(this);
        Bundle bundle = getIntent().getExtras();
        mealType = bundle.getString("meal_type");

        recentListView = findViewById(R.id.food_list_view);

        diaryData.open();

        final List<FoodDiary> diaryList = diaryData.getAllFoodDiary();

        for(FoodDiary a: diaryList) {
            String foodName = a.getFood_name();

            foodData.open();
            Food food = foodData.getFoodByName(foodName);
            int imgId = ImageID(food.getFood_image());
            foodData.close();

            recentList.add(new FoodListHelper(foodName,imgId));
        }

        diaryData.close();


        foodListAdapter= new FoodListAdapter(recentList,this);

        recentListView.setAdapter(foodListAdapter);
        recentListView.setClickable(true);

        recentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                Object obj = recentListView.getAdapter().getItem(itemNumber);
                final String foodName = recentList.get(itemNumber).getName();
                Intent intent = new Intent(RecentListActivity.this, AddFoodToDiaryActivity.class);
                intent.putExtra("foodname", foodName);
                intent.putExtra("meal_type", mealType);
                startActivity(intent);
            }
        });
    }
    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getPackageName());
        return resID;
    }

}
