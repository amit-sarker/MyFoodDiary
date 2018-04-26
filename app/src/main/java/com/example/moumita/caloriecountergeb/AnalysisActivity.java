package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import adapter.SpinnerAdapter;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;

public class AnalysisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView chartCategoryText, chartTypeText;
    private Spinner spinnerChartCategory, spinnerChartType;
    private List<String> chartCategoryList, chartTypeList;
    private List<Integer> chartCategoryImageList, chartTypeImageList;
    private int intChartCategory, intChartType;
    private BarChart mChart;
    private LineChart lineChart;
    private RelativeLayout analysisLayout;
    float barWidth;
    float barSpace;
    float groupSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        TextView text = findViewById(R.id.spinnerTextView);
        ImageView imageView = findViewById(R.id.spinnerImages);
        chartCategoryText = findViewById(R.id.chart_category_text);
        chartTypeText = findViewById(R.id.chart_type_text);
        spinnerChartCategory = findViewById(R.id.chart_category_spinner);
        spinnerChartType = findViewById(R.id.chart_type_spinner);
        analysisLayout = findViewById(R.id.analysis_layout);


        chartCategoryList = new ArrayList<>();
        chartCategoryImageList = new ArrayList<>();
        chartTypeList = new ArrayList<>();
        chartTypeImageList = new ArrayList<>();

        chartCategoryList.add("Calorie Chart");
        chartCategoryList.add("Protein Chart");
        chartCategoryList.add("Carbohydrate Chart");
        chartCategoryList.add("Fat Chart");

        chartTypeList.add("Bar Chart");
        chartTypeList.add("Line Chart");

        chartCategoryImageList.add(ImageID("food2"));
        chartCategoryImageList.add(ImageID("food3"));
        chartCategoryImageList.add(ImageID("food4"));
        chartCategoryImageList.add(ImageID("food5"));

        chartTypeImageList.add(ImageID("food2"));
        chartTypeImageList.add(ImageID("food3"));

        //drawSelectedChart("Carbohydrate Chart", "Bar Chart");

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, chartCategoryList, chartCategoryImageList);
        spinnerChartCategory.setAdapter(spinnerAdapter);

        intChartCategory = spinnerChartCategory.getSelectedItemPosition();

        SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(this, R.layout.spinner_value_layout, chartTypeList, chartTypeImageList);
        spinnerChartType.setAdapter(spinnerAdapter2);

        intChartType = spinnerChartType.getSelectedItemPosition();

        spinnerChartCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                intChartCategory = position;
                System.out.println("IIIIIIIIIIIIIIIIIIIII      " + intChartType + "  IIIIIIIIIIIIIIIIIII   " + intChartCategory);

                /*if(intChartCategory == 0) {
                    LayoutInflater layoutInflater = (LayoutInflater)
                            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    analysisLayout.addView(1, layoutInflater.inflate(R.layout.activity_barchart, this, false) );
                }*/

                drawSelectedChart(chartCategoryList.get(intChartCategory), chartTypeList.get(intChartType));

                spinnerChartType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position2, long l) {
                        intChartType = position2;

                        drawSelectedChart(chartCategoryList.get(intChartCategory), chartTypeList.get(intChartType));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerChartType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                intChartType = position;
                System.out.println("IIIIIIIIIIIIIIIIIIIII      " + intChartType + "  IIIIIIIIIIIIIIIIIII   " + intChartCategory);
                drawSelectedChart(chartCategoryList.get(intChartCategory), chartTypeList.get(intChartType));

                spinnerChartCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position2, long l) {
                        intChartCategory = position2;

                        drawSelectedChart(chartCategoryList.get(intChartCategory), chartTypeList.get(intChartType));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getPackageName());
        return resID;
    }

    public void drawSelectedChart(String chartCategory, String chartType) {
        TrackingOperations trackingData;

        trackingData = new TrackingOperations(this);
        trackingData.open();
        long rowCount = trackingData.getRowCount();
        List<CalorieTracking> lastSevenDayList;
        lastSevenDayList = trackingData.getTrackingData(rowCount - 7);
        trackingData.close();
        List<Double> calorieNeeded = new ArrayList<>();
        List<Double> calorieConsumed = new ArrayList<>();
        List<Double> proteinNeeded = new ArrayList<>();
        List<Double> proteinConsumed = new ArrayList<>();
        List<Double> carbsNeeded = new ArrayList<>();
        List<Double> carbsConsumed = new ArrayList<>();
        List<Double> fatNeeded = new ArrayList<>();
        List<Double> fatConsumed = new ArrayList<>();

        for(int i = 0; i < lastSevenDayList.size(); i++) {
            calorieNeeded.add(lastSevenDayList.get(i).getCal_needed());
            calorieConsumed.add(lastSevenDayList.get(i).getCal_consumed());

            proteinNeeded.add(lastSevenDayList.get(i).getProtein_needed());
            proteinConsumed.add(lastSevenDayList.get(i).getProtein_consumed());

            carbsNeeded.add(lastSevenDayList.get(i).getCarbs_needed());
            carbsConsumed.add(lastSevenDayList.get(i).getCarbs_consumed());

            fatNeeded.add(lastSevenDayList.get(i).getFat_needed());
            fatConsumed.add(lastSevenDayList.get(i).getFat_consumed());
        }

        if(chartCategory.equals("Calorie Chart") && chartType.equals("Line Chart")) drawLineChart(calorieNeeded, calorieConsumed, lastSevenDayList, "Calorie");
        else if(chartCategory.equals("Calorie Chart") && chartType.equals("Bar Chart")) drawBarChart(calorieNeeded, calorieConsumed, lastSevenDayList, "Calorie");
        else if(chartCategory.equals("Protein Chart") && chartType.equals("Line Chart")) drawLineChart(proteinNeeded, proteinConsumed, lastSevenDayList, "Protein");
        else if(chartCategory.equals("Protein Chart") && chartType.equals("Bar Chart")) drawBarChart(proteinNeeded, proteinConsumed, lastSevenDayList, "Protein");
        else if(chartCategory.equals("Carbohydrate Chart") && chartType.equals("Line Chart")) drawLineChart(carbsNeeded, carbsConsumed, lastSevenDayList, "Carbohydrate");
        else if(chartCategory.equals("Carbohydrate Chart") && chartType.equals("Bar Chart")) drawBarChart(carbsNeeded, carbsConsumed, lastSevenDayList, "Carbohydrate");
        else if(chartCategory.equals("Fat Chart") && chartType.equals("Line Chart")) drawLineChart(fatNeeded, fatConsumed, lastSevenDayList, "Fat");
        else if(chartCategory.equals("Fat Chart") && chartType.equals("Bar Chart")) drawBarChart(fatNeeded, fatConsumed, lastSevenDayList, "Fat");
    }

    public void drawLineChart(List<Double> needed, List<Double> consumed, List<CalorieTracking> lastSevenDayList, String type) {

        if(mChart != null) {
            mChart.setVisibility(View.INVISIBLE);
        }

        if(lineChart != null) {
            lineChart.invalidate();
            lineChart.setVisibility(View.INVISIBLE);
        }

        LayoutInflater layoutInflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        analysisLayout.addView(layoutInflater.inflate(R.layout.activity_linechart, null), 5 );

        lineChart = findViewById(R.id.line_chart);

        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);

        // set an alternative background color
        lineChart.setBackgroundColor(Color.LTGRAY);

        // add data
        //setData(20, 30, type);


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


        yVals1.add(new Entry(0, (float) Math.round(needed.get(0))));
        yVals2.add(new Entry(0, (float) Math.round(consumed.get(0))));
        yVals1.add(new Entry(1, (float) Math.round(needed.get(1))));
        yVals2.add(new Entry(1, (float) Math.round(consumed.get(1))));
        yVals1.add(new Entry(2, (float) Math.round(needed.get(2))));
        yVals2.add(new Entry(2, (float) Math.round(consumed.get(2))));
        yVals1.add(new Entry(3, (float) Math.round(needed.get(3))));
        yVals2.add(new Entry(3, (float) Math.round(consumed.get(3))));
        yVals1.add(new Entry(4, (float) Math.round(needed.get(4))));
        yVals2.add(new Entry(4, (float) Math.round(consumed.get(4))));
        yVals1.add(new Entry(5, (float) Math.round(needed.get(5))));
        yVals2.add(new Entry(5, (float) Math.round(consumed.get(5))));
        yVals1.add(new Entry(6, (float) Math.round(needed.get(6))));
        yVals2.add(new Entry(6, (float) Math.round(consumed.get(6))));



        LineDataSet set1, set2;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, type + " Needed");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, type + " Consumed");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            // create a data object with the datasets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            lineChart.setData(data);
        }

        lineChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = lineChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMinimum(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));


        String str = String.valueOf(needed.get(0));
        YAxis leftAxis = lineChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(Float.parseFloat(str) + 1000);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        float x = leftAxis.getAxisMaximum();

        YAxis rightAxis = lineChart.getAxisRight();
        //rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.RED);
        //rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(0);
        rightAxis.setAxisMaximum(x);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

    }

    public void drawBarChart(List<Double> needed, List<Double> consumed, List<CalorieTracking> lastSevenDayList, String type) {

        //mChart.setOnChartValueSelectedListener(this);
        BarDataSet set1 = null, set2 = null;

        if(lineChart != null) {
            lineChart.setVisibility(View.INVISIBLE);
        }

        if(mChart != null) {
            mChart.invalidate();
            mChart.setVisibility(View.INVISIBLE);

        }

        LayoutInflater layoutInflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        analysisLayout.addView(layoutInflater.inflate(R.layout.activity_barchart, null), 0);

        mChart = findViewById(R.id.chart1);

        mChart.getDescription().setEnabled(false);

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
        //l.setTypeface(mTfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);



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


        yVals1.add(new BarEntry(1, (float) Math.round(needed.get(0))));
        yVals2.add(new BarEntry(1, (float) Math.round(consumed.get(0))));
        yVals1.add(new BarEntry(2, (float) Math.round(needed.get(1))));
        yVals2.add(new BarEntry(2, (float) Math.round(consumed.get(1))));
        yVals1.add(new BarEntry(3, (float) Math.round(needed.get(2))));
        yVals2.add(new BarEntry(3, (float) Math.round(consumed.get(2))));
        yVals1.add(new BarEntry(4, (float) Math.round(needed.get(3))));
        yVals2.add(new BarEntry(4, (float) Math.round(consumed.get(3))));
        yVals1.add(new BarEntry(5, (float) Math.round(needed.get(4))));
        yVals2.add(new BarEntry(5, (float) Math.round(consumed.get(4))));
        yVals1.add(new BarEntry(6, (float) Math.round(needed.get(5))));
        yVals2.add(new BarEntry(6, (float) Math.round(consumed.get(5))));
        yVals1.add(new BarEntry(7, (float) Math.round(needed.get(6))));
        yVals2.add(new BarEntry(7, (float) Math.round(consumed.get(6))));


        set1 = new BarDataSet(yVals1, type + " Needed");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, type + " Consumed");
        set2.setColor(Color.BLUE);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new DefaultValueFormatter(0));
        //data.setValueFormatter(new LargeValueFormatter());
        mChart.setData(data);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.getData().setHighlightEnabled(false);
        mChart.invalidate();

        //X-axis
        //XAxis xAxis = mChart.getXAxis();
        //xAxis.setGranularity(1f);
        //xAxis.setGranularityEnabled(true);
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        mChart.getAxisRight().setEnabled(false);
        //YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        //leftAxis.setDrawGridLines(true);
        //leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        mChart.animateY(700);
    }

    private void setData(int count, float range, String title) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count-1; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 450;
            yVals2.add(new Entry(i, val));
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Entry> yVals3 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 500;
            yVals3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) lineChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(yVals3, "DataSet 3");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the datasets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            lineChart.setData(data);
        }
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
