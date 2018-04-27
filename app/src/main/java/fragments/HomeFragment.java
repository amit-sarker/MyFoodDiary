//fabs added from: https://github.com/yavski/fab-speed-dial
// license to be included: http://www.apache.org/licenses/LICENSE-2.0

package fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import addfood.AddFoodActivity;

import helper.InitialShowFood;
import adapter.InitialShowFoodAdapter;
import com.example.moumita.caloriecountergeb.R;
import helper.ShowFood;
import adapter.ShowFoodAdapter;
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
import fooddiarydatabase.DiaryOperations;
import fooddiarydatabase.FoodDiary;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private DiaryOperations foodDiary;
    private List<FoodDiary> foodDiaryList;

    private FoodOperations foodData;

    private FitChart fitChart;
    private PersonOperations personData;
    private ArrayList<ShowFood> showBreakfastModels, showLunchModels, showDinnerModels;
    private ArrayList<InitialShowFood> initialShowBreakfastModels, initialShowLunchModels, initialShowDinnerModels;
    private ListView breakfastListView, lunchListView, dinnerListView;
    private static ShowFoodAdapter showFoodAdapter;
    private static InitialShowFoodAdapter initialShowFoodAdapter;
    private ImageButton waterAddBtn, waterMinusBtn;
    private TextView waterCountText, infoText;
    private int glassOfWater;
    public static String myMealType;
    private TrackingOperations trackingData;
    private CalorieTracking lastTrackingRow;
    private double calConsumed, calRemain, calNeed, carbsConsumed, carbsRemain, carbsNeed, proteinConsumed, proteinRemain,
            proteinNeed, fatConsumed, fatRemain, fatNeed;
    private TextView calConsumedText, calRemainText, calBurnText, calConsumeNum, calBurnNum, calRemainNum,
            carbsBarText, carbsRemainText, proteinBarText, proteinRemainText, fatBarText, fatRemainText;
    private Button addBreakdfastBtn, addLunchBtn, addDinnerBtn;
    private Typeface mTfLight, mTfRegular, mtfBold;
    private ProgressBar carbsBar, proteinBar, fatBar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        personData = new PersonOperations(getContext());
        foodDiary = new DiaryOperations(getContext());
        foodData = new FoodOperations(getContext());
        trackingData = new TrackingOperations(getContext());
        lastTrackingRow = new CalorieTracking();
        calConsumedText = view.findViewById(R.id.cal_consumed_text);
        calRemainText = view.findViewById(R.id.cal_remain_text);
        calBurnText = view.findViewById(R.id.cal_burn_text);
        infoText = view.findViewById(R.id.calorie_text);
        calConsumeNum = view.findViewById(R.id.cal_consumed_number);
        calRemainNum = view.findViewById(R.id.cal_remain_num);
        calBurnNum = view.findViewById(R.id.cal_burn_number);


        carbsBar = view.findViewById(R.id.progress_carbs);
        carbsBarText = view.findViewById(R.id.carbs_bar_text);
        carbsRemainText = view.findViewById(R.id.carbs_bar_remain_text);

        proteinBar = view.findViewById(R.id.progress_protein);
        proteinBarText = view.findViewById(R.id.protein_bar_text);
        proteinRemainText = view.findViewById(R.id.protein_bar_remain_text);

        fatBar = view.findViewById(R.id.progress_fat);
        fatBarText = view.findViewById(R.id.fat_bar_text);
        fatRemainText = view.findViewById(R.id.fat_bar_remain_text);


        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mtfBold = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");

        infoText.setTypeface(mTfRegular);
        infoText.setTextSize(25);
        calConsumeNum.setTypeface(mTfRegular);
        calBurnNum.setTypeface(mTfRegular);
        calConsumedText.setTypeface(mTfLight);
        calBurnText.setTypeface(mTfLight);
        calRemainText.setTypeface(mTfLight);
        calRemainNum.setTypeface(mTfRegular);


        carbsBarText.setTypeface(mTfRegular);
        carbsBarText.setText("CARBS");
        carbsBarText.setTextSize(15);
        carbsRemainText.setTypeface(mTfLight);
        carbsRemainText.setTextSize(12);

        proteinBarText.setTypeface(mTfRegular);
        proteinBarText.setText("PROTEIN");
        proteinBarText.setTextSize(15);
        proteinRemainText.setTypeface(mTfLight);
        proteinRemainText.setTextSize(12);

        fatBarText.setTypeface(mTfRegular);
        fatBarText.setText("FAT");
        fatBarText.setTextSize(15);
        fatRemainText.setTypeface(mTfLight);
        fatRemainText.setTextSize(12);



        personData.open();

        Person person = new Person();
        person = personData.getPerson(1);

        personData.close();
        String cal_needed = person.getBMRWithActivity();

        fitChart = view.findViewById(R.id.fitChart);

        trackingData.open();
        long totalRow = trackingData.getRowCount();
        lastTrackingRow = trackingData.getTracking(totalRow);
        trackingData.close();

        calConsumed = lastTrackingRow.getCal_consumed();
        calRemain = lastTrackingRow.getCal_remaining();
        calNeed = lastTrackingRow.getCal_needed();

        carbsConsumed = lastTrackingRow.getCarbs_consumed();
        carbsRemain = lastTrackingRow.getCarbs_remaining();
        carbsNeed = lastTrackingRow.getCarbs_needed();

        proteinConsumed = lastTrackingRow.getProtein_consumed();
        proteinRemain = lastTrackingRow.getProtein_remaining();
        proteinNeed = lastTrackingRow.getProtein_needed();

        fatConsumed = lastTrackingRow.getFat_consumed();
        fatRemain = lastTrackingRow.getFat_remaining();
        fatNeed = lastTrackingRow.getFat_needed();

        carbsRemainText.setText(Math.round(carbsRemain) + "g left");
        proteinRemainText.setText(Math.round(proteinRemain) + "g left");
        fatRemainText.setText(Math.round(fatRemain) + "g left");




        fitChart.setMinValue(0f);
        fitChart.setMaxValue((float) calNeed);

        Resources resources = getResources();
        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float) calConsumed, resources.getColor(R.color.chart_value_1)));
        fitChart.setValues(values);


        carbsBar.setMax((int) carbsNeed);
        carbsBar.setProgress((int) carbsConsumed);

        proteinBar.setMax((int) proteinNeed);
        proteinBar.setProgress((int) proteinConsumed);

        fatBar.setMax((int) fatNeed);
        fatBar.setProgress((int) fatConsumed);


        calConsumeNum.setText(String.valueOf(Math.round(calConsumed)));
        calBurnNum.setText("0");
        calRemainNum.setText(String.valueOf(Math.round(calRemain)));
        calConsumeNum.setTextSize(25);
        calBurnNum.setTextSize(25);
        calRemainNum.setTextSize(35);

        calConsumedText.setText("KCAL EATEN");
        calConsumedText.setTextSize(10);
        calRemainText.setText("KCAL LEFT");
        calRemainText.setTextSize(15);
        calBurnText.setText("KCAL BURNED");
        calBurnText.setTextSize(10);



        FabSpeedDial fabAddFood = (FabSpeedDial) view.findViewById(R.id.fab_add_food);
        fabAddFood.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }
        });
        fabAddFood.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

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
        addBreakdfastBtn = view.findViewById(R.id.add_breakfast_btn);
        addLunchBtn = view.findViewById(R.id.add_lunch_btn);
        addDinnerBtn = view.findViewById(R.id.add_dinner_btn);


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

        addBreakdfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFoodActivity.class);
                intent.putExtra("meal_type", "Breakfast");
                startActivity(intent);
            }
        });

        addLunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFoodActivity.class);
                intent.putExtra("meal_type", "Lunch");
                startActivity(intent);
            }
        });

        addDinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFoodActivity.class);
                intent.putExtra("meal_type", "Dinner");
                startActivity(intent);
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
                    Intent intent = new Intent(getContext(), AddFoodActivity.class);
                    intent.putExtra("meal_type", "Breakfast");
                    startActivity(intent);

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
                    Intent intent = new Intent(getContext(), AddFoodActivity.class);
                    intent.putExtra("meal_type", "Lunch");
                    startActivity(intent);


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
                    Intent intent = new Intent(getContext(), AddFoodActivity.class);
                    intent.putExtra("meal_type", "Dinner");
                    startActivity(intent);

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



