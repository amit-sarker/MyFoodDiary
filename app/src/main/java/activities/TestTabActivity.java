package activities;



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
import android.widget.Toast;

import com.example.moumita.caloriecountergeb.R;

import fragments.TabFragment;


public class TestTabActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_test_tab);

        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.main_drawer) ;

        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        params.width = width;
        mNavigationView.setLayoutParams(params);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //setTitle(menuItem.getTitle());

                if (menuItem.getItemId() == R.id.drawer_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.drawer_facebook){
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_gPlus){
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_rate) {
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_more) {
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_profile) {
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_share) {
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }
                if (menuItem.getItemId() == R.id.drawer_youtube) {
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_exit) {
                    Toast.makeText(TestTabActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                return false;
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();



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


}