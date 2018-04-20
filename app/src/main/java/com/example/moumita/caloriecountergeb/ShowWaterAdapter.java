package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowWaterAdapter extends ArrayAdapter<ShowWater> {

    public List<ShowWater> mItems;
    public LayoutInflater mInflater;
    public int mResource;
    public int mTextResId;
    Context context;

    public ShowWaterAdapter( Context context, int resourceId, int textViewResourceId, List<ShowWater> objects ) {
        super( context, resourceId, textViewResourceId, objects );
        mInflater = LayoutInflater.from( context );
        mResource = resourceId;
        mTextResId = textViewResourceId;
        mItems = objects;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId( int position ) {
        return getItem( position ).hashCode();
    }
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType( int position ) {
        return position%3;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        View v = null;
        ShowWater showWater = mItems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.homepage_water_list_row, parent,

                    false);
        } else {
            v = convertView;
        }


        Button button =  convertView.findViewById( R.id.water_full );
        button.setText( position+1 );
        button.setVisibility(View.VISIBLE);

        return v;
    }
}



/*public class ShowWaterAdapter extends BaseAdapter {
    public ArrayList<ShowWater> mShowWater;
    Context context;

    public ShowWaterAdapter(ArrayList<ShowWater> mShowWater, Context

            context) {
        this.context = context;
        this.mShowWater = mShowWater;
    }

    @Override
    public int getCount() {
        return mShowWater.size();
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
        ShowWater showWater = mShowWater.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.homepage_list_row, parent,

                    false);
        } else {
            v = convertView;
        }
        Button waterButton = v.findViewById(R.id.water_full);
        waterButton.setText(position+1);
        waterButton.setVisibility(View.VISIBLE);


        return v;

    }

}*/