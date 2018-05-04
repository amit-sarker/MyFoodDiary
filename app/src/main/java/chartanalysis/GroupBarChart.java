package chartanalysis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moumita.caloriecountergeb.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

import generalpersonactivities.BMICalculation;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

public class GroupBarChart extends AppCompatActivity {

    private BarChart chartCalorie, chartProtein, chartFat, chartCarbs;
    float barWidth;
    float barSpace;
    float groupSpace;
    private TrackingOperations trackingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_bar_chart);

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;

        chartCalorie = (BarChart) findViewById(R.id.barChart);
        chartProtein = findViewById(R.id.barChart2);
        chartCarbs = findViewById(R.id.barChart3);
        chartFat = findViewById(R.id.barChart4);

        trackingData = new TrackingOperations(this);

        trackingData.open();
        long lastRow = trackingData.getRowCount();
        List<CalorieTracking> lastSevenDayList = new ArrayList<>();
        lastSevenDayList = trackingData.getTrackingData(lastRow - 7);
        trackingData.close();

        drawChartCalorie(lastSevenDayList, chartCalorie);
        drawChartProtein(lastSevenDayList, chartProtein);
        drawChartCarbs(lastSevenDayList, chartCarbs);
        drawChartFat(lastSevenDayList, chartFat);
    }


    public void drawChartCalorie(List<CalorieTracking> lastSevenDayList, BarChart chartCalorie) {
        chartCalorie.setDescription(null);
        chartCalorie.setPinchZoom(true);
        chartCalorie.setScaleEnabled(false);
        chartCalorie.setDrawBarShadow(false);
        chartCalorie.setDrawGridBackground(false);

        int groupCount = 7;

        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for (int i = 0; i < lastSevenDayList.size(); i++) {
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
        chartCalorie.setData(data);
        chartCalorie.getBarData().setBarWidth(barWidth);
        chartCalorie.getXAxis().setAxisMinimum(0);
        chartCalorie.getXAxis().setAxisMaximum(0 + chartCalorie.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chartCalorie.groupBars(0, groupSpace, barSpace);
        chartCalorie.getData().setHighlightEnabled(false);
        chartCalorie.invalidate();

        Legend l = chartCalorie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(10f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chartCalorie.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        //Y-axis
        chartCalorie.getAxisRight().setEnabled(false);
        YAxis leftAxis = chartCalorie.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        chartCalorie.animateY(700);
    }


    public void drawChartProtein(List<CalorieTracking> lastSevenDayList, BarChart chartCalorie) {
        chartCalorie.setDescription(null);
        chartCalorie.setPinchZoom(false);
        chartCalorie.setScaleEnabled(false);
        chartCalorie.setDrawBarShadow(false);
        chartCalorie.setDrawGridBackground(false);
        int groupCount = 7;

        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for (int i = 0; i < lastSevenDayList.size(); i++) {
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

        yVals1.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getProtein_needed())));
        yVals2.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getProtein_consumed())));
        yVals1.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getProtein_needed())));
        yVals2.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getProtein_consumed())));
        yVals1.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getProtein_needed())));
        yVals2.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getProtein_consumed())));
        yVals1.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getProtein_needed())));
        yVals2.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getProtein_consumed())));
        yVals1.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getProtein_needed())));
        yVals2.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getProtein_consumed())));
        yVals1.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getProtein_needed())));
        yVals2.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getProtein_consumed())));
        yVals1.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getProtein_needed())));
        yVals2.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getProtein_consumed())));

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Proten Needed");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, "Protein Consumed");
        set2.setColor(Color.BLUE);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new DefaultValueFormatter(0));
        chartCalorie.setData(data);
        chartCalorie.getBarData().setBarWidth(barWidth);
        chartCalorie.getXAxis().setAxisMinimum(0);
        chartCalorie.getXAxis().setAxisMaximum(0 + chartCalorie.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chartCalorie.groupBars(0, groupSpace, barSpace);
        chartCalorie.getData().setHighlightEnabled(false);
        chartCalorie.invalidate();

        Legend l = chartCalorie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(10f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chartCalorie.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        //Y-axis
        chartCalorie.getAxisRight().setEnabled(false);
        YAxis leftAxis = chartCalorie.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        chartCalorie.animateY(700);

    }


    public void drawChartCarbs(List<CalorieTracking> lastSevenDayList, BarChart chartCalorie) {
        chartCalorie.setDescription(null);
        chartCalorie.setPinchZoom(false);
        chartCalorie.setScaleEnabled(false);
        chartCalorie.setDrawBarShadow(false);
        chartCalorie.setDrawGridBackground(false);
        int groupCount = 7;

        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for (int i = 0; i < lastSevenDayList.size(); i++) {
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

        yVals1.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getCarbs_needed())));
        yVals2.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getCarbs_consumed())));
        yVals1.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getCarbs_needed())));
        yVals2.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getCarbs_consumed())));
        yVals1.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getCarbs_needed())));
        yVals2.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getCarbs_consumed())));
        yVals1.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getCarbs_needed())));
        yVals2.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getCarbs_consumed())));
        yVals1.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getCarbs_needed())));
        yVals2.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getCarbs_consumed())));
        yVals1.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getCarbs_needed())));
        yVals2.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getCarbs_consumed())));
        yVals1.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getCarbs_needed())));
        yVals2.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getCarbs_consumed())));

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Carbohydrate Needed");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, "Carbohydrate Consumed");
        set2.setColor(Color.BLUE);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new DefaultValueFormatter(0));
        chartCalorie.setData(data);
        chartCalorie.getBarData().setBarWidth(barWidth);
        chartCalorie.getXAxis().setAxisMinimum(0);
        chartCalorie.getXAxis().setAxisMaximum(0 + chartCalorie.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chartCalorie.groupBars(0, groupSpace, barSpace);
        chartCalorie.getData().setHighlightEnabled(false);
        chartCalorie.invalidate();

        Legend l = chartCalorie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(10f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chartCalorie.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        //Y-axis
        chartCalorie.getAxisRight().setEnabled(false);
        YAxis leftAxis = chartCalorie.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        chartCalorie.animateY(700);

    }


    public void drawChartFat(List<CalorieTracking> lastSevenDayList, BarChart chartCalorie) {
        chartCalorie.setDescription(null);
        chartCalorie.setPinchZoom(false);
        chartCalorie.setScaleEnabled(false);
        chartCalorie.setDrawBarShadow(false);
        chartCalorie.setDrawGridBackground(false);
        int groupCount = 7;

        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for (int i = 0; i < lastSevenDayList.size(); i++) {
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

        yVals1.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getFat_needed())));
        yVals2.add(new BarEntry(1, (float) Math.round(lastSevenDayList.get(0).getFat_consumed())));
        yVals1.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getFat_needed())));
        yVals2.add(new BarEntry(2, (float) Math.round(lastSevenDayList.get(1).getFat_consumed())));
        yVals1.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getFat_needed())));
        yVals2.add(new BarEntry(3, (float) Math.round(lastSevenDayList.get(2).getFat_consumed())));
        yVals1.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getFat_needed())));
        yVals2.add(new BarEntry(4, (float) Math.round(lastSevenDayList.get(3).getFat_consumed())));
        yVals1.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getFat_needed())));
        yVals2.add(new BarEntry(5, (float) Math.round(lastSevenDayList.get(4).getFat_consumed())));
        yVals1.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getFat_needed())));
        yVals2.add(new BarEntry(6, (float) Math.round(lastSevenDayList.get(5).getFat_consumed())));
        yVals1.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getFat_needed())));
        yVals2.add(new BarEntry(7, (float) Math.round(lastSevenDayList.get(6).getFat_consumed())));

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Fat Needed");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, "Fat Consumed");
        set2.setColor(Color.BLUE);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new DefaultValueFormatter(0));
        chartCalorie.setData(data);
        chartCalorie.getBarData().setBarWidth(barWidth);
        chartCalorie.getXAxis().setAxisMinimum(0);
        chartCalorie.getXAxis().setAxisMaximum(0 + chartCalorie.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chartCalorie.groupBars(0, groupSpace, barSpace);
        chartCalorie.getData().setHighlightEnabled(false);
        chartCalorie.invalidate();

        Legend l = chartCalorie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(10f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chartCalorie.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        chartCalorie.getAxisRight().setEnabled(false);
        YAxis leftAxis = chartCalorie.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        chartCalorie.animateY(700);

    }

    public String getMonth(String month) {
        if (month.equals("01")) return " Jan";
        else if (month.equals("02")) return " Feb";
        else if (month.equals("03")) return " Mar";
        else if (month.equals("04")) return " Apr";
        else if (month.equals("05")) return " May";
        else if (month.equals("06")) return " Jun";
        else if (month.equals("07")) return " Jul";
        else if (month.equals("08")) return " Aug";
        else if (month.equals("09")) return " Sep";
        else if (month.equals("10")) return " Oct";
        else if (month.equals("11")) return " Nov";
        else return " Dec";
    }

}
