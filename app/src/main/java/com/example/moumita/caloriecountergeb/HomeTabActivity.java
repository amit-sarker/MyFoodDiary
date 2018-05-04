package com.example.moumita.caloriecountergeb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import chartanalysis.AnalysisActivity;
import userprofile.UpdateGoalActivity;
import userprofile.UserProfileActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fooddiarydatabase.DiaryOperations;
import fooddiarydatabase.FoodDiary;
import fragments.TabFragment;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import goaldatabase.Goal;
import goaldatabase.GoalOperations;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;
import userinfo.UserGenderInfoActivity;


public class HomeTabActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static Activity homeTabActivity;
    private TrackingOperations trackingData;
    private GoalOperations goalData;
    private DiaryOperations diaryData;
    private PersonOperations personData;
    private List<String> dateList, distDayCount;

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home_tab);

        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        homeTabActivity = this;

        trackingData = new TrackingOperations(this);
        goalData = new GoalOperations(this);
        diaryData = new DiaryOperations(this);
        personData = new PersonOperations(this);
        dateList = new ArrayList<>();
        distDayCount = new ArrayList<>();

        personData.open();

        final Person person = personData.getPerson(personData.getRowCount());
        personData.close();

        try {
            CheckGoalBreakFast();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //CheckWaterGoal();

        try {
            Check14KCalorieGoal();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                String itemName = (String) menuItem.getTitle();
                closeDrawer();
                switch (menuItem.getItemId()) {

                    case R.id.signout:
                        //FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeTabActivity.this, UserGenderInfoActivity.class);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.friends:
                        Intent intent1 = new Intent(HomeTabActivity.this, UserProfileActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.item_edit:
                        Intent intent2 = new Intent(HomeTabActivity.this, UpdateGoalActivity.class);
                        intent2.putExtra("currentweight", person.getWeight());
                        startActivity(intent2);
                        break;
                    case R.id.charts:
                        Intent intent3 = new Intent(HomeTabActivity.this, AnalysisActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.item_profile:
                        Intent intent5 = new Intent(HomeTabActivity.this, UserProfileActivity.class);
                        startActivity(intent5);
                        break;
                }

                return true;



                /*menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.drawer_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.drawer_facebook){
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_gPlus){
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_rate) {
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_more) {
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_profile) {
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_share) {
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }
                if (menuItem.getItemId() == R.id.drawer_youtube) {
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_exit) {
                    Toast.makeText(HomeTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                return false;*/
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();



    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void CheckGoalBreakFast() throws ParseException {

        goalData.open();
        diaryData.open();
        trackingData.open();

        Goal goal = goalData.getGoal(1);
        long duration = goal.getGoal_duration();

        Calendar cal = Calendar.getInstance();
        //cal.setTime(dateInstance);
        cal.add(Calendar.DATE, -3);
        Date threeDaysAgo = cal.getTime();

        //System.err.println("DATEEEEEEEEE     " + threeDaysAgo);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(threeDaysAgo);
        //System.err.println("Newwwwwwwwwwwwwwwwwww     " + strDate);

        List<FoodDiary> foodDiaryList = diaryData.getFoodDiaryByDate(strDate);

        for(int i = 0; i < foodDiaryList.size(); i++) {
            dateList.add(foodDiaryList.get(i).getDate());
        }

        for(String s: dateList){
            if(!distDayCount.contains(s)){
                distDayCount.add(s);
            }
        }

        long count = 0;
        boolean check;

        //System.err.println("Distinct Dateeeeeeeeeeeeeeeeeeee      " + distDayCount.size());
        String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        for(int i = 0; i < 3; i++) {
            check = checkStreak(current_date_str, foodDiaryList);
            if(!check) break;
            else {
                count++;

                Calendar calendar = Calendar.getInstance();
                //cal.setTime(dateInstance);
                calendar.add(Calendar.DATE, -1);
                Date date = calendar.getTime();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                current_date_str = formatter1.format(date);
            }
        }

        System.err.println("Breakfastttttttttttttttttttttt     " + count);

        Goal updatedGoal = new Goal();

        updatedGoal.setGoal_id(goal.getGoal_id());
        updatedGoal.setGoal_name(goal.getGoal_name());
        updatedGoal.setGoal_description(goal.getGoal_description());
        updatedGoal.setGoal_duration(goal.getGoal_duration());
        updatedGoal.setMy_goal_streak(count);
        updatedGoal.setGoal_status(goal.getGoal_status());
        updatedGoal.setGoal_image(goal.getGoal_image());
        if(count >= 3) {
            updatedGoal.setGoal_completion("yes");
        } else {
            updatedGoal.setGoal_completion(goal.getGoal_completion());
        }
        updatedGoal.setGoal_point(goal.getGoal_point());

        goalData.updateGoal(updatedGoal);

        CalorieTracking lastTrackingRow = trackingData.getTracking(trackingData.getRowCount());
        //new CalorieTracking();
        CalorieTracking newTrackingRow;

        newTrackingRow = lastTrackingRow;
        if(count >= 3) {
            newTrackingRow.setGoal_point(newTrackingRow.getGoal_point() + goal.getGoal_point());
        }

        trackingData.updateTracking(newTrackingRow);

        goalData.close();
        diaryData.close();
        trackingData.close();

    }

    public boolean checkStreak(String date, List<FoodDiary> foodDiaryList) {
        boolean check = false;
        for(int i = 0; i < foodDiaryList.size(); i++) {
            if(foodDiaryList.get(i).getDate().equals(date)) {
                if(foodDiaryList.get(i).getMeal_type().equals("Breakfast")) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public void CheckWaterGoal() {
        trackingData.open();
        goalData.open();

        Goal goal = goalData.getGoal(2);

        CalorieTracking calorieTracking = new CalorieTracking();
        calorieTracking = trackingData.getTracking(trackingData.getRowCount());

        double waterCount = calorieTracking.getWater_consumed();

        long count = 0;
        boolean check;
        String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        for(int i = 0; i < 3; i++) {
            check = checkStreakwater(current_date_str);
            if(!check) break;
            else {
                count++;

                Calendar calendar = Calendar.getInstance();
                //cal.setTime(dateInstance);
                calendar.add(Calendar.DATE, -1);
                Date date = calendar.getTime();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                current_date_str = formatter1.format(date);
            }
        }

        Goal updatedGoal;

        updatedGoal = goal;
        updatedGoal.setMy_goal_streak(count);

        if(count >= 3) {
            updatedGoal.setGoal_completion("yes");
        } else {
            updatedGoal.setGoal_completion(goal.getGoal_completion());
        }

        goalData.updateGoal(updatedGoal);

        CalorieTracking lastTrackingRow = trackingData.getTracking(trackingData.getRowCount());
        //new CalorieTracking();
        CalorieTracking newTrackingRow;

        newTrackingRow = lastTrackingRow;
        if(count >= 3) {
            newTrackingRow.setGoal_point(newTrackingRow.getGoal_point() + goal.getGoal_point());
        }

        trackingData.updateTracking(newTrackingRow);

        goalData.close();
        trackingData.close();

    }

    public boolean checkStreakwater(String date) {
        double waterCount = trackingData.getTrackingByDay(date);
        if(waterCount >= 8) return true;
        else return false;
    }

    public void Check14KCalorieGoal() throws ParseException {
        goalData.open();
        diaryData.open();
        trackingData.open();

        Goal goal = goalData.getGoal(3);

        Calendar cal = Calendar.getInstance();
        //cal.setTime(dateInstance);
        cal.add(Calendar.DATE, -7);
        Date sevenDaysAgo = cal.getTime();

        //System.err.println("DATEEEEEEEEE     " + threeDaysAgo);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(sevenDaysAgo);
        //System.err.println("Newwwwwwwwwwwwwwwwwww     " + strDate);

        List<Double> calorieConsumed = trackingData.getTrackingByCalorie(strDate);

        Collections.reverse(calorieConsumed);

        long count = 0;

        boolean check;
        for(int i = 0; i < calorieConsumed.size(); i++) {
            if(calorieConsumed.get(i) >= 2500.0)
                count++;
            else break;
        }

        Goal updatedGoal = new Goal();

        updatedGoal.setGoal_id(goal.getGoal_id());
        updatedGoal.setGoal_name(goal.getGoal_name());
        updatedGoal.setGoal_description(goal.getGoal_description());
        updatedGoal.setGoal_duration(goal.getGoal_duration());
        updatedGoal.setMy_goal_streak(count);
        updatedGoal.setGoal_status(goal.getGoal_status());
        updatedGoal.setGoal_image(goal.getGoal_image());
        if(count >= 7) {
            updatedGoal.setGoal_completion("yes");
        } else {
            updatedGoal.setGoal_completion(goal.getGoal_completion());
        }
        updatedGoal.setGoal_point(goal.getGoal_point());

        goalData.updateGoal(updatedGoal);

        CalorieTracking lastTrackingRow = trackingData.getTracking(trackingData.getRowCount());
        //new CalorieTracking();
        CalorieTracking newTrackingRow;

        newTrackingRow = lastTrackingRow;
        if(count >= 7) {
            newTrackingRow.setGoal_point(newTrackingRow.getGoal_point() + goal.getGoal_point());
        }

        trackingData.updateTracking(newTrackingRow);

        goalData.close();
        diaryData.close();
        trackingData.close();
    }

}