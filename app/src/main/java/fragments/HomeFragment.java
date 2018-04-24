//fabs added from: https://github.com/yavski/fab-speed-dial
// license to be included: http://www.apache.org/licenses/LICENSE-2.0

package fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.AddFoodActivity;
import com.example.moumita.caloriecountergeb.AddFoodToDiaryActivity;
import com.example.moumita.caloriecountergeb.InitialShowFood;
import com.example.moumita.caloriecountergeb.InitialShowFoodAdapter;
import com.example.moumita.caloriecountergeb.R;
import com.example.moumita.caloriecountergeb.ShowFood;
import com.example.moumita.caloriecountergeb.ShowFoodAdapter;
import com.example.moumita.caloriecountergeb.ShowWater;
import com.example.moumita.caloriecountergeb.ShowWaterAdapter;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fooddatabase.Food;
import fooddatabase.FoodOperations;
import fooddiarydatabase.DiaryDBHandler;
import fooddiarydatabase.DiaryOperations;
import fooddiarydatabase.FoodDiary;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import it.sephiroth.android.library.widget.HListView;
import servingdatabase.FoodServing;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private DiaryOperations foodDiary;
    private List<FoodDiary> foodDiaryList;

    private FoodOperations foodData;

    private FitChart fitChart;
    private Button addButton;
    private PersonOperations personData;
    private ArrayList<ShowFood> showBreakfastModels, showLunchModels, showDinnerModels;
    private ArrayList<InitialShowFood> initialShowBreakfastModels, initialShowLunchModels, initialShowDinnerModels;
    private ListView breakfastListView, lunchListView, dinnerListView;
    private static ShowFoodAdapter showFoodAdapter;
    private static InitialShowFoodAdapter initialShowFoodAdapter;
    private ImageButton waterAddBtn, waterMinusBtn;
    private TextView waterCountText;
    private int glassOfWater;
    public static String myMealType;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        personData = new PersonOperations(getContext());
        foodDiary = new DiaryOperations(getContext());
        foodData = new FoodOperations(getContext());
        personData.open();

        Person person = new Person();
        person = personData.getPerson(1);

        personData.close();
        String cal_needed = person.getBMRWithActivity();

        fitChart = view.findViewById(R.id.fitChart);

        fitChart.setMinValue(0f);
        fitChart.setMaxValue(Float.parseFloat(cal_needed));


        addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources resources = getResources();
                Collection<FitChartValue> values = new ArrayList<>();
                values.add(new FitChartValue(30f, resources.getColor(R.color.chart_value_1)));
                values.add(new FitChartValue(20f, resources.getColor(R.color.chart_value_2)));
                values.add(new FitChartValue(15f, resources.getColor(R.color.chart_value_3)));
                values.add(new FitChartValue(10f, resources.getColor(R.color.chart_value_4)));
                fitChart.setValues(values);
            }
        });



        FabSpeedDial fabAddFood = (FabSpeedDial) view.findViewById(R.id.fab_add_food);
        fabAddFood.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return true;
            }
        });
        fabAddFood.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                //TODO: Start some activity
                switch (menuItem.getItemId()) {
                    case R.id.action_breakfast: {
                        myMealType = "Breakfast";
                        Intent intent = new Intent(getContext(), AddFoodActivity.class);
                        intent.putExtra("meal_type", "Breakfast");
                        startActivity(intent);
                        break;
                    }
                    case R.id.action_lunch: {
                        myMealType = "Lunch";
                        Intent intent = new Intent(getContext(), AddFoodActivity.class);
                        intent.putExtra("meal_type", "Lunch");
                        startActivity(intent);
                        break;
                    }
                    case R.id.action_dinner: {
                        myMealType = "Dinner";
                        Intent intent = new Intent(getContext(), AddFoodActivity.class);
                        intent.putExtra("meal_type", "Dinner");
                        startActivity(intent);
                        break;
                    }


                }
                return false;
            }

        });

        glassOfWater = 0;
        waterAddBtn = view.findViewById(R.id.water_plus_btn);
        waterMinusBtn = view.findViewById(R.id.water_minus_btn);
        waterCountText = view.findViewById(R.id.water_count_text);

        waterAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassOfWater++;
                waterCountText.setText(glassOfWater + " glasses of water");
            }
        });
        waterMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassOfWater--;
                waterCountText.setText(glassOfWater + " glasses of water");
            }
        });


        //Adding Listview

        breakfastListView=view.findViewById(R.id.show_breakfast_list);
        lunchListView=view.findViewById(R.id.show_lunch_list);
        dinnerListView=view.findViewById(R.id.show_dinner_list);


        showBreakfastModels= new ArrayList<>();
        initialShowBreakfastModels = new ArrayList<>();
        showLunchModels = new ArrayList<>();
        initialShowLunchModels = new ArrayList<>();
        showDinnerModels = new ArrayList<>();
        initialShowDinnerModels = new ArrayList<>();


        foodDiary.open();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        foodDiaryList = foodDiary.getFoodListByMealType(currentDate,"Breakfast");
        for (FoodDiary a : foodDiaryList) {
            String foodName = a.getFood_name();
            foodData.open();
            Food food = foodData.getFoodByName(foodName);
            int imgId = ImageID(food.getFood_image());

            showBreakfastModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), a.getFood_serving_amount() + ", " + a.getFood_serving_measurement()));
            foodData.close();
        }



        for(int i = 0; i < foodDiaryList.size(); i++) {
            System.err.println("Diary List:       " + foodDiaryList.get(i).toString());
        }
        for(int i = 0; i < showBreakfastModels.size(); i++) {
            System.err.println("show Breakfast      " + showBreakfastModels.get(i).toString());
        }

        foodDiaryList = foodDiary.getFoodListByMealType(currentDate,"Lunch");
        for (FoodDiary a : foodDiaryList) {
            String foodName = a.getFood_name();
            foodData.open();
            Food food = foodData.getFoodByName(foodName);
            int imgId = ImageID(food.getFood_image());
            showLunchModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), a.getFood_serving_amount() + ", " + a.getFood_serving_measurement()));
            foodData.close();
        }

        foodDiaryList = foodDiary.getFoodListByMealType(currentDate,"Dinner");
        for (FoodDiary a : foodDiaryList) {
            String foodName = a.getFood_name();
            foodData.open();
            Food food = foodData.getFoodByName(foodName);
            int imgId = ImageID(food.getFood_image());

            showDinnerModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), a.getFood_serving_amount() + ", " + a.getFood_serving_measurement()));
            foodData.close();
        }

        foodDiary.close();



        initialShowBreakfastModels.add(new InitialShowFood(R.drawable.breakfast,"Eat breakfast, Start Healthy Life"));
        if(showBreakfastModels.isEmpty()==true)
        {
            initialShowFoodAdapter= new InitialShowFoodAdapter(initialShowBreakfastModels,getContext());

            breakfastListView.setAdapter(initialShowFoodAdapter);
            breakfastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InitialShowFood initialShowFood= initialShowBreakfastModels.get(position);

                }
            });

        }
        else
        {
            showFoodAdapter= new ShowFoodAdapter(showBreakfastModels,getContext());

            breakfastListView.setAdapter(showFoodAdapter);
            breakfastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShowFood showFoodModel= showBreakfastModels.get(position);

                }
            });
        }


        initialShowLunchModels.add(new InitialShowFood(R.drawable.lunch,"Take a break, Take lunch"));
        if(showLunchModels.isEmpty()==true)
        {
            initialShowFoodAdapter= new InitialShowFoodAdapter(initialShowLunchModels,getContext());

            lunchListView.setAdapter(initialShowFoodAdapter);
            lunchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InitialShowFood initialShowFood= initialShowLunchModels.get(position);

                }
            });

        }
        else
        {
            showFoodAdapter= new ShowFoodAdapter(showLunchModels,getContext());

            lunchListView.setAdapter(showFoodAdapter);
            lunchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShowFood showFoodModel= showLunchModels.get(position);

                }
            });
        }


        initialShowDinnerModels.add(new InitialShowFood(R.drawable.dinner_demo,"End of the day? Gift yourself a wonderful dinner"));
        if(showDinnerModels.isEmpty()==true)
        {
            initialShowFoodAdapter= new InitialShowFoodAdapter(initialShowDinnerModels,getContext());

            dinnerListView.setAdapter(initialShowFoodAdapter);
            dinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InitialShowFood initialShowFood= initialShowDinnerModels.get(position);

                }
            });

        }
        else
        {
            showFoodAdapter= new ShowFoodAdapter(showDinnerModels,getContext());

            dinnerListView.setAdapter(showFoodAdapter);
            dinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShowFood showFoodModel= showDinnerModels.get(position);

                }
            });
        }



        setListViewHeightBasedOnItems(breakfastListView);
        setListViewHeightBasedOnItems(lunchListView);
        setListViewHeightBasedOnItems(dinnerListView);

        return view;

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }
    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getActivity().getPackageName());
        return resID;
    }



}



