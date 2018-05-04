package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.moumita.caloriecountergeb.Features;
import com.example.moumita.caloriecountergeb.GeneticAlgoKnapsack;
import com.example.moumita.caloriecountergeb.R;

import java.util.ArrayList;
import java.util.List;

import activities.HomeTabActivity;
import adapter.ShowFoodAdapter;
import addfood.AddFoodToDiaryActivity;
import fooddatabase.Food;
import fooddatabase.FoodOperations;
import helper.ShowFood;
import notifications.TestActivity;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;


public class SuggestionsFragment extends Fragment {

    private Button refreshBtn;
    private ListView breakfastListView, lunchListView, dinnerListView;
    private static ShowFoodAdapter showFoodAdapter;
    private FoodOperations foodData;
    private TrackingOperations trackingData;
    private CalorieTracking lastTrackingRow;
    private double calRemain, carbsRemain, proteinRemain, fatRemain;
    private ArrayList<Features> value_of_items;
    private ArrayList<ShowFood> breakfastFoodSuggestions, lunchFoodSuggestions, dinnerFoodSuggetions;

    private List<Food> foodList = new ArrayList<>();

    public SuggestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);

        //Adding Listview
        breakfastListView=view.findViewById(R.id.show_breakfast_list);
        lunchListView=view.findViewById(R.id.show_lunch_list);
        dinnerListView=view.findViewById(R.id.show_dinner_list);
        refreshBtn = view.findViewById(R.id.refresh_btn);

        value_of_items = new ArrayList<>();
        breakfastFoodSuggestions = new ArrayList<>();
        lunchFoodSuggestions = new ArrayList<>();
        dinnerFoodSuggetions = new ArrayList<>();
        foodData = new FoodOperations(getContext());
        trackingData = new TrackingOperations(getContext());

        foodData.open();

        foodList = foodData.getAllFood();

        foodData.close();

        getInput(foodList);

        trackingData.open();
        long totalRow = trackingData.getRowCount();
        lastTrackingRow = trackingData.getTracking(totalRow);
        trackingData.close();

        calRemain = lastTrackingRow.getCal_remaining();
        carbsRemain = lastTrackingRow.getCarbs_remaining();
        proteinRemain = lastTrackingRow.getProtein_remaining();
        fatRemain = lastTrackingRow.getFat_remaining();
        final Features features = new Features(calRemain, proteinRemain, fatRemain, calRemain, "foodname", 1.0, 23097898);
        GeneticAlgoKnapsack knap1 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
        breakfastFoodSuggestions = knap1.showOptimalList();
        GeneticAlgoKnapsack knap2 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
        lunchFoodSuggestions = knap2.showOptimalList();
        GeneticAlgoKnapsack knap3 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
        dinnerFoodSuggetions = knap3.showOptimalList();

        while (breakfastFoodSuggestions.isEmpty()==true) {
            knap1 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
            breakfastFoodSuggestions = knap1.showOptimalList();

        }
        showFoodAdapter = new ShowFoodAdapter(breakfastFoodSuggestions, getContext());

        breakfastListView.setAdapter(showFoodAdapter);
        breakfastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowFood showFoodModel = breakfastFoodSuggestions.get(position);
                Intent intent = new Intent(getContext(), AddFoodToDiaryActivity.class);
                intent.putExtra("meal_type", "Breakfast");
                intent.putExtra("foodname", showFoodModel.getFoodName());
                startActivity(intent);

            }
        });

        while (lunchFoodSuggestions.isEmpty()==true) {
            knap2 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
            lunchFoodSuggestions = knap2.showOptimalList();

        }
        showFoodAdapter = new ShowFoodAdapter(lunchFoodSuggestions, getContext());

        lunchListView.setAdapter(showFoodAdapter);
        lunchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowFood showFoodModel = lunchFoodSuggestions.get(position);
                Intent intent = new Intent(getContext(), AddFoodToDiaryActivity.class);
                intent.putExtra("meal_type", "Lunch");
                intent.putExtra("foodname", showFoodModel.getFoodName());
                startActivity(intent);

            }
        });
        while (dinnerFoodSuggetions.isEmpty()==true) {
            knap3 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
            dinnerFoodSuggetions = knap3.showOptimalList();

        }
        showFoodAdapter= new ShowFoodAdapter(dinnerFoodSuggetions,getContext());

        dinnerListView.setAdapter(showFoodAdapter);
        dinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowFood showFoodModel= dinnerFoodSuggetions.get(position);
                Intent intent = new Intent(getContext(), AddFoodToDiaryActivity.class);
                intent.putExtra("foodname", showFoodModel.getFoodName());
                intent.putExtra("meal_type", "Dinner");
                startActivity(intent);

            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneticAlgoKnapsack knap1 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
                breakfastFoodSuggestions = knap1.showOptimalList();
                GeneticAlgoKnapsack knap2 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
                lunchFoodSuggestions = knap2.showOptimalList();
                GeneticAlgoKnapsack knap3 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
                dinnerFoodSuggetions = knap3.showOptimalList();

                while (breakfastFoodSuggestions.isEmpty()==true) {
                    knap1 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
                    breakfastFoodSuggestions = knap1.showOptimalList();

                }
                showFoodAdapter = new ShowFoodAdapter(breakfastFoodSuggestions, getContext());

                breakfastListView.setAdapter(showFoodAdapter);
                breakfastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ShowFood showFoodModel = breakfastFoodSuggestions.get(position);
                        Intent intent = new Intent(getContext(), AddFoodToDiaryActivity.class);
                        intent.putExtra("meal_type", "Breakfast");
                        intent.putExtra("foodname", showFoodModel.getFoodName());
                        startActivity(intent);

                    }
                });

                while (lunchFoodSuggestions.isEmpty()==true) {
                    knap2 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
                    lunchFoodSuggestions = knap2.showOptimalList();

                }
                showFoodAdapter = new ShowFoodAdapter(lunchFoodSuggestions, getContext());

                lunchListView.setAdapter(showFoodAdapter);
                lunchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ShowFood showFoodModel = lunchFoodSuggestions.get(position);
                        Intent intent = new Intent(getContext(), AddFoodToDiaryActivity.class);
                        intent.putExtra("meal_type", "Lunch");
                        intent.putExtra("foodname", showFoodModel.getFoodName());
                        startActivity(intent);

                    }
                });
                while (dinnerFoodSuggetions.isEmpty()==true) {
                    knap3 = new GeneticAlgoKnapsack(value_of_items, features,100, 100, 80, 5);
                    dinnerFoodSuggetions = knap3.showOptimalList();

                }
                showFoodAdapter= new ShowFoodAdapter(dinnerFoodSuggetions,getContext());

                dinnerListView.setAdapter(showFoodAdapter);
                dinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ShowFood showFoodModel= dinnerFoodSuggetions.get(position);
                        Intent intent = new Intent(getContext(), AddFoodToDiaryActivity.class);
                        intent.putExtra("meal_type", "Dinner");
                        intent.putExtra("foodname", showFoodModel.getFoodName());
                        startActivity(intent);

                    }
                });

                setListViewHeightBasedOnItems(breakfastListView);
                setListViewHeightBasedOnItems(lunchListView);
                setListViewHeightBasedOnItems(dinnerListView);
            }
        });


        setListViewHeightBasedOnItems(breakfastListView);
        setListViewHeightBasedOnItems(lunchListView);
        setListViewHeightBasedOnItems(dinnerListView);

        /*Log.e("optimal","Optimal Listttttttttttttttttt size: " + foodSuggestions.size());
        System.err.println("Optimal Listttttttttttttttttt size: " + foodSuggestions.size());
        for(int i=0;i<foodSuggestions.size();i++) {
            Log.e("list","food Sugesssssssssssssssssssssss  : " + foodSuggestions.get(i).getFoodName());
            System.err.println("food Sugesssssssssssssssssssssss  : " + foodSuggestions.get(i).getFoodName());
        }
        */
        return view;

    }


    private void getInput(List<Food> foodList) {

        for(int i = 0; i < foodList.size(); i++) {
            double cal = foodList.get(i).getFood_energy();
            double pro = foodList.get(i).getFood_proteins();
            double fat = foodList.get(i).getFood_fat();
            double carb = foodList.get(i).getFood_carbohydrates();
            value_of_items.add(new Features(cal,pro,fat,carb, foodList.get(i).getFood_name(), foodList.get(i).getFood_serving_size(),ImageID(foodList.get(i).getFood_image())));
        }

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