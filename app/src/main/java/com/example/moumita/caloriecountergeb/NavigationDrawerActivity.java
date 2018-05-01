package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import userinfo.UserGenderInfoActivity;


public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Button srch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        setupToolbarMenu();
        setupNavigationDrawerMenu();
        srch = findViewById(R.id.search_button);

    }

    private void setupToolbarMenu() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("CalorieCounterGEB");
    }

    private void setupNavigationDrawerMenu() {

        NavigationView navigationView = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override // Called when Any Navigation Item is Clicked
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        String itemName = (String) menuItem.getTitle();
        closeDrawer();
        switch (menuItem.getItemId()) {

            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(NavigationDrawerActivity.this, UserGenderInfoActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.friends:
                Intent intent1 = new Intent(NavigationDrawerActivity.this, UserProfileActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.item_edit:
                Intent intent2 = new Intent(NavigationDrawerActivity.this, UpdateGoalActivity.class);
                intent2.putExtra("user", "Driver");
                startActivity(intent2);
                break;
            case R.id.charts:
                Intent intent3 = new Intent(NavigationDrawerActivity.this, AnalysisActivity.class);
                startActivity(intent3);
                break;

            case R.id.item_profile:
                Intent intent5 = new Intent(NavigationDrawerActivity.this, UserProfileActivity.class);
                startActivity(intent5);
                break;
        }

        return true;
    }

    // Close the Drawer
    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // Open the Drawer
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }



    public void addContentView(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutId, null, false);
        mDrawerLayout.addView(contentView, 0);
    }
}