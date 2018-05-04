package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.moumita.caloriecountergeb.R;

import helper.ShowWater;

import java.util.List;

public class ShowWaterAdapter extends ArrayAdapter<ShowWater> {

    public List<ShowWater> mItems;
    public LayoutInflater mInflater;
    public int mResource;
    public int mTextResId;
    Context context;

    public ShowWaterAdapter(Context context, int resourceId, int textViewResourceId, List<ShowWater> objects) {
        super(context, resourceId, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
        mResource = resourceId;
        mTextResId = textViewResourceId;
        mItems = objects;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = null;
        ShowWater showWater = mItems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.homepage_water_list_row, parent, false);
        } else {
            v = convertView;
        }


        Button button = convertView.findViewById(R.id.water_full);
        button.setText(position + 1);
        button.setVisibility(View.VISIBLE);

        return v;
    }
}
