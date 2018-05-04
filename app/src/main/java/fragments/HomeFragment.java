//fabs added from: https://github.com/yavski/fab-speed-dial
// license to be included: http://www.apache.org/licenses/LICENSE-2.0

package fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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

import generalpersonactivities.BMICalculation;
import goaldatabase.Goal;
import goaldatabase.GoalOperations;
import helper.InitialShowFood;
import adapter.InitialShowFoodAdapter;

import userprofile.CurrentWeightUpdateGoal;
import userprofile.DialogActivity;
import com.example.moumita.caloriecountergeb.R;
import helper.ShowFood;
import adapter.ShowFoodAdapter;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import mehdi.sakout.fancybuttons.FancyButton;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private DiaryOperations foodDiary;
    private List<FoodDiary> foodDiaryList;

    private FoodOperations foodData;
    private CardView weightCard;
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
            carbsBarText, carbsRemainText, proteinBarText, proteinRemainText, fatBarText, fatRemainText, waterText,
            breakfastText, dinnerText, lunchText;
    private TextView goalWeightText, gainedWeightText, goalWeightNumText, currentWeightNumText, weightMotivationText;
    private FancyButton updateWeightButton;
    private ProgressBar weightProgressBar;
    private Button addBreakdfastBtn, addLunchBtn, addDinnerBtn;
    private Typeface mTfLight, mTfRegular, mtfBold;
    private ProgressBar carbsBar, proteinBar, fatBar;
    private Vibrator vibrator;
    private GoalOperations goalData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


/*
        TapTargetView.showFor(getActivity(),                 // `this` is an Activity
                TapTarget.forView(view.findViewById(R.id.fab_add_food), "Click here to add your first food", "Select from category - breakfast, lunch or dinner")
                        // All options below are optional
                        .outerCircleColor(R.color.red)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.colorWhite)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.colorPrimary)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        Toast.makeText(getContext(), " touched", Toast.LENGTH_SHORT).show();


                    }
                });
*/




        personData = new PersonOperations(getContext());
        foodDiary = new DiaryOperations(getContext());
        foodData = new FoodOperations(getContext());
        trackingData = new TrackingOperations(getContext());
        goalData = new GoalOperations(getContext());
        lastTrackingRow = new CalorieTracking();
        weightCard = view.findViewById(R.id.weight_card_view);
        calConsumedText = view.findViewById(R.id.cal_consumed_text);
        calRemainText = view.findViewById(R.id.cal_remain_text);
        calBurnText = view.findViewById(R.id.cal_burn_text);
        infoText = view.findViewById(R.id.calorie_text);
        calConsumeNum = view.findViewById(R.id.cal_consumed_number);
        calRemainNum = view.findViewById(R.id.cal_remain_num);
        calBurnNum = view.findViewById(R.id.cal_burn_number);

        goalWeightText = view.findViewById(R.id.goal_weight_text);
        gainedWeightText = view.findViewById(R.id.gained_weight_text);
        goalWeightNumText = view.findViewById(R.id.current_goal_weight_text);
        currentWeightNumText = view.findViewById(R.id.current_weight_text);
        weightMotivationText = view.findViewById(R.id.goal_weight_motivation_text);
        updateWeightButton = view.findViewById(R.id.btn_update_weight);
        weightProgressBar = view.findViewById(R.id.progress_weight);


        carbsBar = view.findViewById(R.id.progress_carbs);
        carbsBarText = view.findViewById(R.id.carbs_bar_text);
        carbsRemainText = view.findViewById(R.id.carbs_bar_remain_text);

        proteinBar = view.findViewById(R.id.progress_protein);
        proteinBarText = view.findViewById(R.id.protein_bar_text);
        proteinRemainText = view.findViewById(R.id.protein_bar_remain_text);

        fatBar = view.findViewById(R.id.progress_fat);
        fatBarText = view.findViewById(R.id.fat_bar_text);
        fatRemainText = view.findViewById(R.id.fat_bar_remain_text);

        waterAddBtn = view.findViewById(R.id.water_plus_btn);
        waterMinusBtn = view.findViewById(R.id.water_minus_btn);
        waterCountText = view.findViewById(R.id.water_count_text);
        addBreakdfastBtn = view.findViewById(R.id.add_breakfast_btn);
        addLunchBtn = view.findViewById(R.id.add_lunch_btn);
        addDinnerBtn = view.findViewById(R.id.add_dinner_btn);

        waterText = view.findViewById(R.id.water_text);
        breakfastText = view.findViewById(R.id.breakfast_text);
        dinnerText = view.findViewById(R.id.dinner_text);
        lunchText = view.findViewById(R.id.lunch_text);


        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mtfBold = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");

        waterText.setTypeface(mTfRegular);
        breakfastText.setTypeface(mTfRegular);
        dinnerText.setTypeface(mTfRegular);
        lunchText.setTypeface(mTfRegular);


        infoText.setTypeface(mTfRegular);
        infoText.setTextSize(25);
        calConsumeNum.setTypeface(mTfRegular);
        calBurnNum.setTypeface(mTfRegular);
        calConsumedText.setTypeface(mTfLight);
        calBurnText.setTypeface(mTfLight);
        calRemainText.setTypeface(mTfLight);
        calRemainNum.setTypeface(mTfRegular);



        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        CheckWaterGoal();

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

        long last_row_person = personData.getRowCount();
        System.err.println("LLLLLLLLLLLLLLLLLLLLLLLLLL         " + last_row_person);
        Person personLastRow = personData.getPerson(last_row_person);

        personData.close();
        String cal_needed = personLastRow.getBMRWithActivity();
        String current_weight = String.valueOf(BMICalculation.Round(Double.parseDouble(personLastRow.getWeight()), 1));
        String target_weight = personLastRow.getTargetWeight();

        System.err.println("Currentttttttttttttttttttttttttttt:   " + current_weight);
        System.err.println("Targetttttttttttttttttttttttttttt:    " + target_weight);

        fitChart = view.findViewById(R.id.fitChart);

        trackingData.open();
        long totalRow = trackingData.getRowCount();
        lastTrackingRow = trackingData.getTracking(totalRow);
        trackingData.close();

        waterCountText.setText((int)lastTrackingRow.getWater_consumed() + " glasses of water");
        waterCountText.setTypeface(mTfRegular);

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

        if(carbsRemain < 0.0) carbsRemain = 0.0;
        if(proteinRemain < 0.0) proteinRemain = 0.0;
        if(fatRemain < 0.0) fatRemain = 0.0;

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

        if(calConsumed < 0.0) calConsumed = 0.0;
        if(calRemain < 0.0) calRemain = 0.0;

        calConsumeNum.setText(String.valueOf(Math.round(calConsumed)));
        calBurnNum.setText("0");
        calRemainNum.setText(String.valueOf(Math.round(calRemain)));
        calConsumeNum.setTextSize(25);
        calBurnNum.setTextSize(25);
        calRemainNum.setTextSize(35);

        calConsumedText.setText("CAL EATEN");
        calConsumedText.setTextSize(10);
        calRemainText.setText("CAL LEFT");
        calRemainText.setTextSize(15);
        calBurnText.setText("CAL BURNED");
        calBurnText.setTextSize(10);

        personData.open();
        List<Double> updatedWeightAll = personData.getTotalUpdatedweight();
        personData.close();

        double total_updated_weight = 0.0;
        for(int i = 0; i < updatedWeightAll.size(); i++) {
            total_updated_weight += updatedWeightAll.get(i);
        }


        goalWeightText.setTypeface(mTfRegular);
        goalWeightText.setText("GOAL WEIGHT: " + target_weight + " KG");
        goalWeightText.setTextSize(15);

        gainedWeightText.setTypeface(mTfLight);
        gainedWeightText.setText("You've gained " + BMICalculation.Round(total_updated_weight, 1) + "Kg");
        gainedWeightText.setTextSize(30);


        double initial_weight = Double.parseDouble(current_weight) - total_updated_weight;

        weightProgressBar.setMax((int) (Double.parseDouble(target_weight) - initial_weight) * 1000);
        double progress_amount = total_updated_weight;
        weightProgressBar.setProgress((int) (progress_amount * 1000));
        //weightProgressBar.setScaleY(3f);


        currentWeightNumText.setTypeface(mTfRegular);

        currentWeightNumText.setText(BMICalculation.Round(initial_weight, 1) + " kg");
        currentWeightNumText.setTextSize(10);

        goalWeightNumText.setTypeface(mTfRegular);
        goalWeightNumText.setText(target_weight + " kg");
        goalWeightNumText.setTextSize(10);


        weightMotivationText.setTypeface(mTfLight);
        weightMotivationText.setTextSize(15);


        if(Double.parseDouble(personLastRow.getWeight()) >= Double.parseDouble(personLastRow.getTargetWeight())) {
            updateWeightButton.setText("Update Your Goal");
            weightMotivationText.setText(R.string.congrates_goal_complete);
            updateWeightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibrator.vibrate(25);
                    Intent intent = new Intent(getContext(), CurrentWeightUpdateGoal.class);
                    startActivity(intent);
                    //getActivity().finish();
                }
            });
        } else {
            updateWeightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibrator.vibrate(25);
                    Intent intent = new Intent(getContext(), DialogActivity.class);
                    startActivity(intent);
                    //getActivity().finish();
                }
            });
        }


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
                vibrator.vibrate(25);

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

        waterAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);
                trackingData.open();
                CalorieTracking lastRowTracking = trackingData.getTracking(trackingData.getRowCount());
                CalorieTracking newTrackingRow = lastRowTracking;
                glassOfWater = (int) (lastRowTracking.getWater_consumed() + 1);
                newTrackingRow.setWater_consumed(glassOfWater);
                trackingData.updateTracking(newTrackingRow);
                trackingData.close();

                waterCountText.setText(glassOfWater + " glasses of water");
            }
        });
        waterMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);

                trackingData.open();
                CalorieTracking lastRowTracking = trackingData.getTracking(trackingData.getRowCount());
                CalorieTracking newTrackingRow = lastRowTracking;

                glassOfWater = (int) (lastRowTracking.getWater_consumed() - 1);
                if(glassOfWater < 0) glassOfWater = 0;

                newTrackingRow.setWater_consumed(glassOfWater);
                trackingData.updateTracking(newTrackingRow);
                trackingData.close();

                waterCountText.setText(glassOfWater + " glasses of water");
            }
        });

        addBreakdfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);
                Intent intent = new Intent(getContext(), AddFoodActivity.class);
                intent.putExtra("meal_type", "Breakfast");
                startActivity(intent);
            }
        });

        addLunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);
                Intent intent = new Intent(getContext(), AddFoodActivity.class);
                intent.putExtra("meal_type", "Lunch");
                startActivity(intent);
            }
        });

        addDinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);
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
            if(a.getFood_serving_amount().equals("") || a.getFood_serving_measurement().equals("")) {
                showBreakfastModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), "not available" + ", " +"not available" ));

            }
            showBreakfastModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), a.getFood_serving_amount() + ", " + a.getFood_serving_measurement()));
            foodData.close();
        }

        foodDiaryList = foodDiary.getFoodListByMealType(currentDate,"Lunch");
        for (FoodDiary a : foodDiaryList) {
            String foodName = a.getFood_name();
            foodData.open();
            Food food = foodData.getFoodByName(foodName);
            int imgId = ImageID(food.getFood_image());
            if(a.getFood_serving_amount().equals("") || a.getFood_serving_measurement().equals("")) {
                showLunchModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), "not available" + ", " +"not available" ));

            }
            showLunchModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), a.getFood_serving_amount() + ", " + a.getFood_serving_measurement()));
            foodData.close();
        }

        foodDiaryList = foodDiary.getFoodListByMealType(currentDate,"Dinner");
        for (FoodDiary a : foodDiaryList) {
            String foodName = a.getFood_name();
            foodData.open();
            Food food = foodData.getFoodByName(foodName);
            int imgId = ImageID(food.getFood_image());

            if(a.getFood_serving_amount().equals("") && a.getFood_serving_measurement().equals("")) {
                showDinnerModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), "not available" + ", " +"not available" ));

            }
            showDinnerModels.add(new ShowFood(imgId, foodName, a.getTotal_cal_selected_food(), a.getFood_serving_amount() + ", " + a.getFood_serving_measurement()));
            foodData.close();
        }

        foodDiary.close();

        initialShowBreakfastModels.add(new InitialShowFood(R.drawable.breakfast,"Eat breakfast, Start Healthy Life"));
        if(showBreakfastModels.isEmpty()==true)
        {
            initialShowFoodAdapter = new InitialShowFoodAdapter(initialShowBreakfastModels,getContext());

            breakfastListView.setAdapter(initialShowFoodAdapter);
            breakfastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    vibrator.vibrate(25);
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
                    vibrator.vibrate(25);
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
                    vibrator.vibrate(25);
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
                    vibrator.vibrate(25);
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
                    vibrator.vibrate(25);
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
                    vibrator.vibrate(25);
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


    public void CheckWaterGoal() {
        trackingData.open();
        goalData.open();

        Goal goal = goalData.getGoal(2);

        CalorieTracking calorieTracking = new CalorieTracking();
        calorieTracking = trackingData.getTracking(trackingData.getRowCount());

        double waterCount = calorieTracking.getWater_consumed();

        long count = 0;
        boolean check;
        String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        for(int i = 0; i < 3; i++) {
            check = checkStreakwater(current_date_str);
            if(!check) break;
            else {
                count++;

                Calendar calendar = Calendar.getInstance();
                //cal.setTime(dateInstance);
                calendar.add(Calendar.DATE, -1);
                Date date = calendar.getTime();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                current_date_str = formatter1.format(date);
            }
        }

        Goal updatedGoal;

        updatedGoal = goal;
        updatedGoal.setMy_goal_streak(count);

        if(count >= 3) {
            updatedGoal.setGoal_completion("yes");
        } else {
            updatedGoal.setGoal_completion(goal.getGoal_completion());
        }

        goalData.updateGoal(updatedGoal);

        CalorieTracking lastTrackingRow = trackingData.getTracking(trackingData.getRowCount());
        //new CalorieTracking();
        CalorieTracking newTrackingRow;

        newTrackingRow = lastTrackingRow;
        if(count >= 3) {
            newTrackingRow.setGoal_point(newTrackingRow.getGoal_point() + goal.getGoal_point());
        }

        trackingData.updateTracking(newTrackingRow);

        goalData.close();
        trackingData.close();

    }

    public boolean checkStreakwater(String date) {
        double waterCount = trackingData.getTrackingByDay(date);
        if(waterCount >= 8) return true;
        else return false;
    }


    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getActivity().getPackageName());
        return resID;
    }
    public void addFood() {

    }
}



