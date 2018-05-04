package userprofile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;
import com.github.mikephil.charting.charts.BarChart;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import piechart.MyAxisValueFormatter;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView coverImage, editImage;
    private CircleImageView profilePic;
    private TextView profileName, profileGender, profileHeight, profileCalorie, profileGoalWeight, profileActivityLevel, profileAge, profileWeight,
            ageVal, activityLevelVal, goalWeightVal, weightVal, calorieVal, heightVal, genderVal, weightProgressText, dailyProgressText;
    private LineChart lineChart;
    private Typeface mTfLight, mTfRegular, mTfBold;
    private PersonOperations personData;
    private List<Person> personList;
    private List<Double> currentWeightList, targetWeightList;
    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        coverImage = findViewById(R.id.header_cover_image);
        profilePic = findViewById(R.id.profile_pic);
        editImage = findViewById(R.id.edit);
        profileName = findViewById(R.id.name);
        profileGender = findViewById(R.id.textView5);
        profileHeight = findViewById(R.id.textView7);
        profileWeight = findViewById(R.id.textView22);
        profileCalorie = findViewById(R.id.textView16);
        profileGoalWeight = findViewById(R.id.textView15);
        profileActivityLevel = findViewById(R.id.textView11);
        profileAge = findViewById(R.id.textView10);

        ageVal = findViewById(R.id.blood_group);
        genderVal = findViewById(R.id.education);
        heightVal = findViewById(R.id.occupation);
        weightVal = findViewById(R.id.mobileNumber);
        activityLevelVal = findViewById(R.id.gender);
        goalWeightVal = findViewById(R.id.marriage);
        calorieVal = findViewById(R.id.dob);

        weightProgressText = findViewById(R.id.profile_activity_text);
        dailyProgressText = findViewById(R.id.daily_weight_progress);
        lineChart = findViewById(R.id.line_chart_weight);
        mChart = findViewById(R.id.bar_chart_weight);

        personData = new PersonOperations(this);
        currentWeightList = new ArrayList<>();
        targetWeightList = new ArrayList<>();

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mTfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        profileName.setTypeface(mTfBold);

        weightProgressText.setTypeface(mTfRegular);
        weightProgressText.setTextSize(25);

        dailyProgressText.setTypeface(mTfRegular);
        dailyProgressText.setTextSize(25);

        personData.open();
        Person person = personData.getPerson(personData.getRowCount());
        personData.close();

        ageVal.setText(person.getAge());
        genderVal.setText(person.getGender());
        heightVal.setText(person.getHeight());
        weightVal.setText(person.getWeight());
        activityLevelVal.setText(ReturnActivityLevel(person.getActivityLevel()));
        goalWeightVal.setText(person.getTargetWeight());
        calorieVal.setText(person.getBMRWithActivity());


        personData.open();
        personList = personData.getAllPersons();
        personData.close();

        for(int i = 0; i < personList.size(); i++) {
            currentWeightList.add(Double.valueOf(personList.get(i).getWeight()));
            targetWeightList.add(Double.valueOf(personList.get(i).getTargetWeight()));
        }

        DrawLineChart(currentWeightList, targetWeightList, personList);
        DailyProgressBarChart(currentWeightList, personList);
    }


    public void DrawLineChart(List<Double> currentWeightList, List<Double> targetWeightList, List<Person> personList) {
        lineChart = findViewById(R.id.line_chart_weight);

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

        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for(int i = 0; i < personList.size(); i++) {
            String date = personList.get(i).getWeightUpdateDate();
            String[] p = date.split("-");
            temp.add(p[2] + getMonth(p[1]));
        }

        for(int i = 0; i < temp.size(); i++) {
            xVals.add(temp.get(i));
        }



        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        for(int i = 0; i < currentWeightList.size(); i++) {
            String temp1 = String.valueOf(targetWeightList.get(i));
            String temp2 = String.valueOf(currentWeightList.get(i));
            float temp3 = Float.parseFloat(temp1);
            float temp4 = Float.parseFloat(temp2);
            yVals1.add(new Entry(i, temp3));
            yVals2.add(new Entry(i, temp4));
        }

        if(yVals1.size() == 0 || yVals2.size() == 0) {
            yVals1.add(new Entry(0, (float) Math.round(0)));
            yVals2.add(new Entry(0, (float) Math.round(0)));
        }


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
            set1 = new LineDataSet(yVals1,  "Target Weight");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(R.color.primary_dark);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new_gif MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "Current Weight");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(ColorTemplate.getHoloBlue());
            set2.setCircleColor(Color.BLACK);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new_gif MyFillFormatter(900f));

            // create a data object with the datasets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            lineChart.setData(data);
        }


        lineChart.animateX(2000);

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        //xAxis.setDrawGridLines(false);
        //xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setAxisMinimum(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        try {

            double maxCurrentWeight = Collections.max(currentWeightList);
            double minCurrentWeight = Collections.min(currentWeightList);
            double maxLeftAxis = Math.max(maxCurrentWeight, targetWeightList.get(0));
            double minLeftAxis = Math.min(minCurrentWeight, targetWeightList.get(0));
            String maxLeft = String.valueOf(maxLeftAxis);
            String minLeft = String.valueOf(minLeftAxis);
            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setTypeface(mTfLight);
            leftAxis.setTextColor(R.color.primary_dark);
            leftAxis.setAxisMaximum(Float.parseFloat(maxLeft) + 10);
            leftAxis.setAxisMinimum(Float.parseFloat(minLeft) - 5);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGranularityEnabled(true);
            float rightMax = leftAxis.getAxisMaximum();
            float rightMin = leftAxis.getAxisMinimum();

            YAxis rightAxis = lineChart.getAxisRight();
            rightAxis.setTypeface(mTfLight);
            rightAxis.setTextColor(ColorTemplate.getHoloBlue());
            //rightAxis.setAxisMaximum(900);
            rightAxis.setAxisMinimum(rightMin);
            rightAxis.setAxisMaximum(rightMax);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawZeroLine(false);
            rightAxis.setGranularityEnabled(false);
        } catch (Exception e) {}

    }

    public void DailyProgressBarChart(List<Double> currentWeightList, List<Person> personList) {
        BarDataSet set1 = null, set2 = null;
        mChart.getDescription().setEnabled(false);

        /*barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;*/

//        mChart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        /*Legend l = mChart.getLegend();
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

        mChart.getAxisRight().setEnabled(false);*/



        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        //xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setTextColor(R.color.colorPrimary);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);



        ArrayList xVals = new ArrayList();
        ArrayList temp = new ArrayList();

        for(int i = 0; i < personList.size(); i++) {
            String date = personList.get(i).getWeightUpdateDate();
            String[] p = date.split("-");
            temp.add(p[2] + getMonth(p[1]));
        }

        for(int i = 0; i < temp.size(); i++) {
            xVals.add(temp.get(i));
        }


        ArrayList yVals1 = new ArrayList();

        for(int i = 0; i < currentWeightList.size(); i++) {
            String temp2 = String.valueOf(currentWeightList.get(i));
            float temp3 = Float.parseFloat(temp2);
            yVals1.add(new BarEntry(i, temp3));
        }

        if(yVals1.size() == 0) {
            yVals1.add(new BarEntry(1, (float) Math.round(0)));
        }

        //set1 = new BarDataSet(yVals1, "Current Weight");
        /*set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
        set1.setValues(yVals1);


        set1.setColor(R.color.primary_dark);

        BarData data = new BarData(set1);*/



        BarData data = new BarData();


        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Daily Progress");

            set1.setDrawIcons(false);

            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }



        //data.setValueFormatter(new DefaultValueFormatter(0));
        //data.setValueFormatter(new_gif LargeValueFormatter());
        //mChart.setData(data);
        //mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
        //mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount); ////////////////////////////////////
        //mChart.groupBars(0, groupSpace, barSpace);

        //mChart.getData().setHighlightEnabled(false);
        mChart.invalidate();

        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
        //X-axis
        //XAxis xAxis = mChart.getXAxis();
        //xAxis.setGranularity(1f);
        //xAxis.setGranularityEnabled(true);
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(currentWeightList.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        mChart.getAxisRight().setEnabled(false);
        //YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setValueFormatter(new LargeValueFormatter());
        //leftAxis.setDrawGridLines(true);
        //leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);
        mChart.animateY(1400);
    }

    public String ReturnActivityLevel(long activityLevel) {
        //  0: Little to no exercise
        // 1: Light exercise (1–3 days per week)
        // 2: Moderate exercise (3–5 days per week)
        // 3: Heavy exercise (6–7 days per week)
        // 4: Very heavy exercise (twice per day, extra heavy workouts)

        if(activityLevel == 0) return "Little to no exercise";
        else if(activityLevel == 1) return "Light exercise (1–3 days per week)";
        else if(activityLevel == 2) return "Moderate exercise (3–5 days per week)";
        else if(activityLevel == 3) return "Heavy exercise (6–7 days per week)";
        else return "Very heavy exercise (twice per day)";
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
