package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import userinfo.UserGenderInfoActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Person User = new Person();
        Intent intent = new Intent(MainActivity.this, UserGenderInfoActivity.class);
        //intent.putExtra("sampleObject", (Serializable) User);
        startActivity(intent);

    }
}
