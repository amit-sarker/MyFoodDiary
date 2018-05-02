package com.example.moumita.caloriecountergeb;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import activities.HomeTabActivity;
//import br.com.goncalves.pugnotification.notification.PugNotification;
import categorydatabase.CategoryDBHandler;
import categorydatabase.CategoryOperations;
import categorydatabase.FoodCategory;
import fooddatabase.Food;
import fooddatabase.FoodDBHandler;
import fooddatabase.FoodOperations;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import goaldatabase.Goal;
import goaldatabase.GoalDBHandler;
import goaldatabase.GoalOperations;
import notifications.TestActivity;
import okhttp3.OkHttpClient;
import servingdatabase.FoodServing;
import servingdatabase.ServingDBHandler;
import servingdatabase.ServingOperations;
import trackingdatabase.CalorieTracking;
import trackingdatabase.TrackingOperations;
import userinfo.UserGenderInfoActivity;
import userinfo.UserWeightInfoActivity;


public class MainActivity extends AppCompatActivity {

    private FoodOperations foodData;
    private CategoryOperations categoryData;
    private ServingOperations servingData;
    private TrackingOperations trackingData;
    private PersonOperations personData;
    private GoalOperations goalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();



        FoodDBHandler foodDBHandler = new FoodDBHandler(this);
        SQLiteDatabase food_database = foodDBHandler.getWritableDatabase();
        food_database.execSQL("DROP TABLE IF EXISTS " + FoodDBHandler.TABLE_FOOD);
        food_database.execSQL(FoodDBHandler.TABLE_CREATE);

        CategoryDBHandler categoryDBHandler = new CategoryDBHandler(this);
        SQLiteDatabase cat_database = categoryDBHandler.getWritableDatabase();
        cat_database.execSQL("DROP TABLE IF EXISTS " + categoryDBHandler.TABLE_CATEGORY);
        cat_database.execSQL(CategoryDBHandler.TABLE_CREATE);

        ServingDBHandler servingDBHandler = new ServingDBHandler(this);
        SQLiteDatabase serv_database = servingDBHandler.getWritableDatabase();
        serv_database.execSQL("DROP TABLE IF EXISTS " + servingDBHandler.TABLE_SERVING);
        serv_database.execSQL(ServingDBHandler.TABLE_CREATE);

        GoalDBHandler goalDBHandler = new GoalDBHandler(this);
        SQLiteDatabase goal_database = goalDBHandler.getWritableDatabase();
        goal_database.execSQL("DROP TABLE IF EXISTS " + goalDBHandler.TABLE_GOAL);
        goal_database.execSQL(GoalDBHandler.TABLE_CREATE);



        foodData = new FoodOperations(this);
        foodData.open();


        try {
            readFromAssetsFood(this,"foodlist.txt", foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        foodData.close();



        categoryData = new CategoryOperations(this);
        categoryData.open();

        try {
            readFromAssetsCategory(this, "category.txt", categoryData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        categoryData.close();


        servingData = new ServingOperations(this);
        servingData.open();

        try {
            readFromAssetsServing(this, "serving.txt", servingData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        servingData.close();


        goalData = new GoalOperations(this);
        goalData.open();

        try {
            readFromAssetsGoal(this, "goal.txt", goalData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        goalData.close();


        personData = new PersonOperations(this);
        trackingData = new TrackingOperations(this);

        trackingData.open();
        if(trackingData.getRowCount() != 0) {
            TrackDataCondition(trackingData, personData);
        } else {}

        trackingData.close();

        personData.open();
        long personRowCount = personData.getRowCount();
        personData.close();

        /*Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
        startActivity(intent);
        finish();*/

        if(personRowCount > 0) {
            Intent intent = new Intent(MainActivity.this, HomeTabActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, UserGenderInfoActivity.class);
            startActivity(intent);
            finish();
        }


        //Push Notification
        Intent breakfastIntent = new Intent(this, AlarmReceiver.class);
        Intent lunchIntent = new Intent(this, AlarmReceiver.class);
        Intent dinnerIntent = new Intent(this, AlarmReceiver.class);
        breakfastIntent.putExtra("alarm_time", "breakfast");
        lunchIntent.putExtra("alarm_time", "lunch");
        dinnerIntent.putExtra("alarm_time", "dinner");

        PendingIntent breakfastBroadcast = PendingIntent.getBroadcast(this, 100, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent lunchBroadcast = PendingIntent.getBroadcast(this, 101, lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dinnerBroadcast = PendingIntent.getBroadcast(this, 102, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //notificationIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        //alarmManager.cancel(broadcast);

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);
        if (now.after(alarmStartTime)) {
            Log.d("Hey","Added a day");
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakfastBroadcast);
        Log.d("Alarm","Alarms set for everyday 8 am.");

        Calendar lunchTime = Calendar.getInstance();
        lunchTime.set(Calendar.HOUR_OF_DAY, 13);
        lunchTime.set(Calendar.MINUTE, 5);
        lunchTime.set(Calendar.SECOND, 0);
        if (now.after(lunchTime)) {
            Log.d("Hey","Added a day");
            lunchTime.add(Calendar.DATE, 1);
        }
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, lunchTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, lunchBroadcast);


        Calendar dinnerTime = Calendar.getInstance();
        dinnerTime.set(Calendar.HOUR_OF_DAY, 21);
        dinnerTime.set(Calendar.MINUTE, 0);
        dinnerTime.set(Calendar.SECOND, 0);
        if (now.after(dinnerTime)) {
            Log.d("Hey","Added a day");
            dinnerTime.add(Calendar.DATE, 1);
        }
        alarmManager3.setRepeating(AlarmManager.RTC_WAKEUP, dinnerTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerBroadcast);

        System.err.println("Timeeeeeeeeeeeeeeeeee " + lunchTime + " " + dinnerTime + " " + now);

    }


    public static String readFromAssetsCategory(Context context, String filename, CategoryOperations categoryData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        categoryToDatabase(mLine, categoryData);
        while (mLine != null) {
            sb.append(mLine);
            mLine = reader.readLine();
            if(mLine != null) {
                categoryToDatabase(mLine, categoryData);
            }
        }
        reader.close();
        return sb.toString();
    }

    public static void categoryToDatabase(String lineFromFile, CategoryOperations categoryData) {
        String categoryAttributes[]= lineFromFile.split(",");

        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setCategoryID(Long.parseLong(categoryAttributes[0]));
        foodCategory.setCategoryName(categoryAttributes[1]);
        foodCategory.setFoodID(Long.parseLong(categoryAttributes[2]));
        foodCategory.setFoodName(categoryAttributes[3]);
        foodCategory.setFoodImage(categoryAttributes[4]);
        foodCategory.setCategoryImage(categoryAttributes[5]);

        for (String eachAttribute: categoryAttributes){
            if(eachAttribute.equals("\0"))
                eachAttribute = "null";
        }
        categoryData.addCategory(foodCategory);
    }

    public static String readFromAssetsFood(Context context, String filename, FoodOperations foodData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        foodToDatabase(mLine, foodData);
        while (mLine != null) {
            sb.append(mLine);
            mLine = reader.readLine();
            if(mLine != null) {
                foodToDatabase(mLine, foodData);
            }
        }
        reader.close();
        return sb.toString();
    }

    public static void foodToDatabase(String lineFromFile, FoodOperations foodData) {
        System.err.println(lineFromFile);
        String foodAttributes[]= lineFromFile.split(",");

        Food food = new Food();
        food.setFood_name(foodAttributes[0]);
        food.setFood_serving_size(Double.parseDouble(foodAttributes[1]));
        food.setFood_serving_mesurment(foodAttributes[2]);
        food.setFood_energy(Double.parseDouble(foodAttributes[3]));
        food.setFood_proteins(Double.parseDouble(foodAttributes[4]));
        food.setFood_carbohydrates(Double.parseDouble(foodAttributes[5]));
        food.setFood_fat(Double.parseDouble(foodAttributes[6]));
        food.setFood_water(Double.parseDouble(foodAttributes[7]));
        food.setFood_fiber(Double.parseDouble(foodAttributes[8]));
        food.setFood_vit_a(Double.parseDouble(foodAttributes[9]));
        food.setFood_vit_c(Double.parseDouble(foodAttributes[10]));
        food.setFood_vit_e(Double.parseDouble(foodAttributes[11]));
        food.setFood_category_id(Long.parseLong(foodAttributes[12]));
        food.setFood_image(foodAttributes[13]);
        food.setFood_notes(foodAttributes[14]);

        for (String eachAttribute: foodAttributes){
            if(eachAttribute.equals("\0"))
                eachAttribute = "null";
        }
        foodData.addFood(food);
    }

    public static String readFromAssetsServing(Context context, String filename, ServingOperations servingData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        servingToDatabase(mLine, servingData);
        while (mLine != null) {
            sb.append(mLine);
            mLine = reader.readLine();
            if(mLine != null) {
                servingToDatabase(mLine, servingData);
            }
        }
        reader.close();
        return sb.toString();
    }

    public static void servingToDatabase(String lineFromFile, ServingOperations servingData) {
        String servingAttributes[]= lineFromFile.split(",");

        FoodServing foodServing = new FoodServing();
        foodServing.setServing_id(Long.parseLong(servingAttributes[0]));
        foodServing.setFood_name(servingAttributes[1]);
        foodServing.setFood_serving_measurement(servingAttributes[2]);
        foodServing.setServing_size_to_grams(servingAttributes[3]);
        foodServing.setServing_image_id(servingAttributes[4]);

        for (String eachAttribute: servingAttributes){
            if(eachAttribute.equals("\0"))
                eachAttribute = "null";
        }
        servingData.addServing(foodServing);
    }

    public static String readFromAssetsGoal(Context context, String filename, GoalOperations goalData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        goalToDatabase(mLine, goalData);
        while (mLine != null) {
            sb.append(mLine);
            mLine = reader.readLine();
            if(mLine != null) {
                goalToDatabase(mLine, goalData);
            }
        }
        reader.close();
        return sb.toString();
    }

    public static void goalToDatabase(String lineFromFile, GoalOperations goalData) {
        String goalAttributes[]= lineFromFile.split(",");

        Goal goal = new Goal();
        goal.setGoal_name(goalAttributes[0]);
        goal.setGoal_description(goalAttributes[1]);
        goal.setGoal_duration(Long.parseLong(goalAttributes[2]));
        goal.setMy_goal_streak(Long.parseLong(goalAttributes[3]));
        goal.setGoal_status(goalAttributes[4]);
        goal.setGoal_image(goalAttributes[5]);
        goal.setGoal_completion(goalAttributes[6]);
        goal.setGoal_point(Long.parseLong(goalAttributes[7]));

        for (String eachAttribute: goalAttributes){
            if(eachAttribute.equals("\0"))
                eachAttribute = "null";
        }
        goalData.addGoal(goal);
    }

    public static void TrackDataCondition(TrackingOperations trackingData, PersonOperations personData) {

        double proteinsWithActivity, fatWithActivity, carbsWithActivity;
        long row_count = trackingData.getRowCount();
        CalorieTracking trackingRow = trackingData.getTracking(row_count);
        String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String tracking_row_date = trackingRow.getDate();

        personData.open();
        long last_row = personData.getRowCount();
        Person person = personData.getPerson(last_row);
        double BMRWithActivity = Double.parseDouble(person.getBMRWithActivity());
        proteinsWithActivity = Math.round(BMRWithActivity * 0.25);
        fatWithActivity = Math.round(BMRWithActivity * 0.25);
        carbsWithActivity = Math.round(BMRWithActivity * 0.5);
        personData.close();

        if(current_date_str.equals(tracking_row_date)) {}
        else {
            CalorieTracking calorieTrackingData = new CalorieTracking();
            calorieTrackingData.setDate(current_date_str);

            calorieTrackingData.setCal_needed(BMRWithActivity);
            calorieTrackingData.setCal_consumed(0.0);
            calorieTrackingData.setCal_remaining(BMRWithActivity);

            calorieTrackingData.setProtein_needed(Math.round(proteinsWithActivity / 4.0));
            calorieTrackingData.setProtein_consumed(0.0);
            calorieTrackingData.setProtein_remaining(Math.round(proteinsWithActivity / 4.0));

            calorieTrackingData.setFat_needed(Math.round(fatWithActivity / 9.0));
            calorieTrackingData.setFat_consumed(0.0);
            calorieTrackingData.setFat_remaining(Math.round(fatWithActivity / 9.0));

            calorieTrackingData.setCarbs_needed(Math.round(carbsWithActivity / 4.0));
            calorieTrackingData.setCarbs_consumed(0.0);
            calorieTrackingData.setCarbs_remaining(Math.round(carbsWithActivity / 4.0));

            trackingData.addTrackingData(calorieTrackingData);
        }
    }
}