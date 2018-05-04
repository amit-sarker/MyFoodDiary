package adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;

import java.util.List;


public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private List<String> contentList;
    private List<Integer> imageList;

    public SpinnerAdapter(Context context, int resource, List<String> objects, List<Integer> imageList) {
        super(context,  R.layout.spinner_value_layout, R.id.spinnerTextView, objects);
        this.ctx = context;
        this.contentList = objects;
        this.imageList = imageList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_value_layout, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(contentList.get(position));


        ImageView imageView = (ImageView)row.findViewById(R.id.spinnerImages);
        imageView.setImageResource(imageList.get(position));

        return row;
    }
}