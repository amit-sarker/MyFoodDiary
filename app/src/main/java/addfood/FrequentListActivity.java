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
import fooddiarydatabase.DiaryOperations;
import helper.FoodListHelper;
import servingdatabase.ServingOperations;
import trackingdatabase.TrackingOperations;

public class FrequentListActivity extends AppCompatActivity {

    /*private Toolbar toolbar;
    private DiaryOperations trackingData;
    private ListView mListView;
    //private List<String> categoryList = new ArrayList<>();
    private String mealType;

    private ListView frequentListView;
    private FoodListAdapter foodListAdapter;
    private List<FoodListHelper> frequentList = new ArrayList<>();
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequent_list);

        /*toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        trackingData = new DiaryOperations(this);
        Bundle bundle = getIntent().getExtras();
        mealType = bundle.getString("meal_type");

        frequentListView = findViewById(R.id.food_list_view);

        trackingData.open();

        final List<FoodCategory> foodCategoryList = trackingData.getFoodCategoryByName(categoryName);

        for(FoodCategory a: foodCategoryList) {
            String categoryName = a.getFoodName();
            String categoryImage = a.getFoodImage();
            frequentList.add(new FoodListHelper(categoryName,ImageID(categoryImage)));
        }

        trackingData.close();


        foodListAdapter= new FoodListAdapter(frequentList,this);

        frequentListView.setAdapter(foodListAdapter);
        frequentListView.setClickable(true);

        frequentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                Object obj = frequentListView.getAdapter().getItem(itemNumber);
                final String foodName = foodCategoryList.get(itemNumber).getFoodName();
                Intent intent = new Intent(FrequentListActivity.this, AddFoodToDiaryActivity.class);
                intent.putExtra("foodname", foodName);
                intent.putExtra("meal_type", mealType);
                startActivity(intent);
            }
        });
        */

    }

    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getPackageName());
        return resID;
    }

}
