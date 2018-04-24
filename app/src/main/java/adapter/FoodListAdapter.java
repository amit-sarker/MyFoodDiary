package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import helper.FoodListHelper;
import com.example.moumita.caloriecountergeb.R;

import java.util.List;

public class FoodListAdapter extends BaseAdapter {
    public List<FoodListHelper> mFoodList;
    Context context;

    public FoodListAdapter(List<FoodListHelper> mFoodList, Context

            context) {
        this.context = context;
        this.mFoodList = mFoodList ;
    }

    @Override
    public int getCount() {
        return mFoodList.size();
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
        FoodListHelper foodList = mFoodList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spinner_value_layout, parent,

                    false);
        } else {
            v = convertView;
        }
        ImageView foodImageView = v.findViewById(R.id.spinnerImages);
        TextView foodNameText = v.findViewById(R.id.spinnerTextView);


        foodImageView.setImageResource(foodList.getImage());
        foodNameText.setText(foodList.getName());

        foodImageView.setVisibility(View.VISIBLE);
        foodNameText.setVisibility(View.VISIBLE);


        return v;

    }


}