package addfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.moumita.caloriecountergeb.R;

import java.util.ArrayList;
import java.util.List;

import fooddatabase.Food;
import fooddatabase.FoodOperations;

public class AddFoodActivity extends AppCompatActivity implements View.OnClickListener {

    private String foodName;
    private Toolbar toolbar;
    private AutoCompleteTextView text;
    private FoodOperations foodData;
    private List<String> foodList = new ArrayList<>();
    private CardView CategoriesCard;
    private CardView RecentCard;
    String mealType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Bundle bundle = getIntent().getExtras();
        mealType = bundle.getString("meal_type");

        CategoriesCard = findViewById(R.id.categories_card);
        RecentCard = findViewById(R.id.recent_card);


        foodData = new FoodOperations(this);

        foodData.open();

        List<Food> getfoodList = foodData.getAllFood();
        for(Food food: getfoodList) {
            foodList.add(food.getFood_name());
        }

        foodData.close();

        text = findViewById(R.id.autoCompleteTextView1);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList);

        text.setAdapter(adapter);
        text.setThreshold(1);
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),adapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });

        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                foodName = adapter.getItem(position).toString();
                Intent intent = new Intent(AddFoodActivity.this, AddFoodToDiaryActivity.class);
                intent.putExtra("foodname", foodName);
                intent.putExtra("meal_type", mealType);
                startActivity(intent);
                finish();
            }
        });

        CategoriesCard.setOnClickListener(this);
        RecentCard.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId())
        {
            case R.id.categories_card:
                intent = new Intent(this, CategoryListActivity.class);
                intent.putExtra("meal_type", mealType);
                startActivity(intent);
                break;
            case R.id.recent_card:
                intent = new Intent(this, RecentListActivity.class);
                intent.putExtra("meal_type", mealType);
                startActivity(intent);
                break;

            default:break;

        }
    }
}
