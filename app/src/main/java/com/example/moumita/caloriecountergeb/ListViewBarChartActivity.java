package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import piechart.DemoBase;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

import static java.lang.Math.min;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 *
 * @author Philipp Jahoda
 */
public class ListViewBarChartActivity extends DemoBase {
    private TrackingOperations trackingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list_view_bar_chart);

        ListView lv = (ListView) findViewById(R.id.listView1);
        trackingData = new TrackingOperations(this);

        trackingData.open();
        long lastRow = trackingData.getRowCount();
        List<CalorieTracking> lastSevenDayList = new ArrayList<>();
        lastSevenDayList = trackingData.getTrackingData(lastRow - 7);
        trackingData.close();

        ArrayList<BarData> list = new ArrayList<BarData>();

        list.add(generateDataCalorie(1, lastSevenDayList));
        list.add(generateDataProtein(1, lastSevenDayList));
        list.add(generateDataCarbs(1, lastSevenDayList));
        list.add(generateDataFat(1, lastSevenDayList));



        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {

        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BarData data = getItem(position);

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // apply styling
            data.setValueTypeface(mTfLight);
            data.setValueTextColor(Color.BLACK);
            holder.chart.getDescription().setEnabled(false);
            holder.chart.setDrawGridBackground(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTfLight);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setTypeface(mTfLight);
            leftAxis.setLabelCount(5, false);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setTypeface(mTfLight);
            rightAxis.setLabelCount(5, false);
            rightAxis.setSpaceTop(15f);

            // set data
            holder.chart.setData(data);
            holder.chart.setFitBars(true);

            // do not forget to refresh the chart
//            holder.chart.invalidate();
            holder.chart.animateY(700);

            return convertView;
        }

        private class ViewHolder {

            BarChart chart;
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataCalorie(int cnt, List<CalorieTracking> lastSevenDayList) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < min(7,lastSevenDayList.size()); i++) {
            entries.add(new BarEntry(i, (float) (lastSevenDayList.get(i).getCal_consumed())));
        }

        BarDataSet d = new BarDataSet(entries, "Calories consumed (last 7 days)");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private BarData generateDataProtein(int cnt, List<CalorieTracking> lastSevenDayList) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, (float) (lastSevenDayList.get(i).getProtein_consumed())));
        }

        BarDataSet d = new BarDataSet(entries, "Proteins consumed (last 7 days)");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private BarData generateDataFat(int cnt, List<CalorieTracking> lastSevenDayList) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, (float) (lastSevenDayList.get(i).getFat_consumed())));
        }

        BarDataSet d = new BarDataSet(entries, "Fat consumed (last 7 days)");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private BarData generateDataCarbs(int cnt, List<CalorieTracking> lastSevenDayList) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, (float) (lastSevenDayList.get(i).getCarbs_consumed())));
        }

        BarDataSet d = new BarDataSet(entries, "Carbohydrate consumed (last 7 days)");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }
}