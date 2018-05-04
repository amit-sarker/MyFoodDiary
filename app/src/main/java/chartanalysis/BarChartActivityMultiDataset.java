package chartanalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import piechart.DemoBase;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

public class BarChartActivityMultiDataset extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    float barWidth;
    float barSpace;
    float groupSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);


        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawValueAboveBar(true);

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;

//        mChart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(mTfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        TrackingOperations trackingData = new TrackingOperations(this);
        trackingData.open();
        long lastRow = trackingData.getRowCount();
        List<CalorieTracking> lastSevenDayList = trackingData.getTrackingData(lastRow - 7);
        trackingData.close();

        DrawBarChart(lastSevenDayList);
    }

    public void DrawBarChart(List<CalorieTracking> lastSevenDayList) {
        int groupCount = 7;

        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for(int i = 0; i < lastSevenDayList.size(); i++) {
            String date = lastSevenDayList.get(i).getDate();
            String[] p = date.split("-");
            temp.add(p[2] + getMonth(p[1]));
        }

        xVals.add(temp.get(0));
        xVals.add(temp.get(1));
        xVals.add(temp.get(2));
        xVals.add(temp.get(3));
        xVals.add(temp.get(4));
        xVals.add(temp.get(5));
        xVals.add(temp.get(6));

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        yVals1.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getCal_needed())));
        yVals2.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getCal_consumed())));
        yVals1.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getCal_needed())));
        yVals2.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getCal_consumed())));
        yVals1.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getCal_needed())));
        yVals2.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getCal_consumed())));
        yVals1.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getCal_needed())));
        yVals2.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getCal_consumed())));
        yVals1.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getCal_needed())));
        yVals2.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getCal_consumed())));
        yVals1.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getCal_needed())));
        yVals2.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getCal_consumed())));
        yVals1.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getCal_needed())));
        yVals2.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getCal_consumed())));

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Calorie Needed");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, "Calorie Consumed");
        set2.setColor(Color.BLUE);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new DefaultValueFormatter(0));
        //data.setValueFormatter(new_gif LargeValueFormatter());
        mChart.setData(data);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.getData().setHighlightEnabled(false);
        mChart.invalidate();

        //X-axis
        XAxis xAxis = mChart.getXAxis();
        //xAxis.setGranularity(1f);
        //xAxis.setGranularityEnabled(true);
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        //leftAxis.setDrawGridLines(true);
        //leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        mChart.animateY(700);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }

    public String getMonth(String month) {
        if(month.equals("01")) return " Jan";
        else if(month.equals("02")) return " Feb";
        else if(month.equals("03")) return " Mar";
        else if(month.equals("04")) return " Apr";
        else if(month.equals("05")) return " May";
        else if(month.equals("06")) return " Jun";
        else if(month.equals("07")) return " Jul";
        else if(month.equals("08")) return " Aug";
        else if(month.equals("09")) return " Sep";
        else if(month.equals("10")) return " Oct";
        else if(month.equals("11")) return " Nov";
        else return " Dec";
    }
}