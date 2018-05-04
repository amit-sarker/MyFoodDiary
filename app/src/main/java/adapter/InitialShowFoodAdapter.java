package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import helper.InitialShowFood;

import com.example.moumita.caloriecountergeb.R;

import java.util.ArrayList;

public class InitialShowFoodAdapter extends BaseAdapter {
    public ArrayList<InitialShowFood> mInitialShowFood;
    Context context;

    public InitialShowFoodAdapter(ArrayList<InitialShowFood> mInitialShowFood, Context context) {
        this.context = context;
        this.mInitialShowFood = mInitialShowFood;
    }

    @Override
    public int getCount() {
        return mInitialShowFood.size();
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
        InitialShowFood initialShowFood = mInitialShowFood.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.homepage_initial__list_row, parent, false);
        } else {
            v = convertView;
        }

        Typeface mTfBold = Typeface.createFromAsset(v.getContext().getAssets(), "OpenSans-Bold.ttf");

        ImageView foodImageView = v.findViewById(R.id.food_speech_image);
        TextView foodNameText = v.findViewById(R.id.food_speech_text);


        foodImageView.setImageResource(initialShowFood.getFoodImage());
        foodNameText.setText(initialShowFood.getMotivationalSpeech());
        foodNameText.setTypeface(mTfBold);

        foodImageView.setVisibility(View.VISIBLE);
        foodNameText.setVisibility(View.VISIBLE);

        return v;
    }

}