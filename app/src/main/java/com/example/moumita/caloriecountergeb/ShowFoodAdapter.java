package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowFoodAdapter extends BaseAdapter {
    public ArrayList<ShowFood> mShowFood;
    Context context;

    public ShowFoodAdapter(ArrayList<ShowFood> mShowFood, Context

            context) {
        this.context = context;
        this.mShowFood = mShowFood;
    }

    @Override
    public int getCount() {
        return mShowFood.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;

        ShowFood showFood = mShowFood.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.homepage_list_row, parent,

                    false);
        } else {
            v = convertView;
        }
        ImageView foodImageView = v.findViewById(R.id.food_image);
        TextView foodNameText = (TextView) v.findViewById(R.id.food_name_text);
        TextView foodCalorieText = (TextView) v.findViewById(R.id.food_calorie_text);
        TextView foodServingText = (TextView) v.findViewById(R.id.food_serving_text);



        foodImageView.setImageResource(showFood.getFoodImage());
        foodNameText.setText(showFood.getFoodName());
        foodCalorieText.setText(showFood.getFoodCalorie());
        foodServingText.setText(showFood.getFoodServingSize());
        foodImageView.setVisibility(View.VISIBLE);
        foodNameText.setVisibility(View.VISIBLE);
        foodCalorieText.setVisibility(View.VISIBLE);
        foodServingText.setVisibility(View.VISIBLE);

        return v;

    }

}