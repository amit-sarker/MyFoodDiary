package com.example.moumita.caloriecountergeb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class AddFoodToDiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);

        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.addfood_recyclerview);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    }
}
