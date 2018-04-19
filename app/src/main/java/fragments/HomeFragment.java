//fabs added from: https://github.com/yavski/fab-speed-dial
// license to be included: http://www.apache.org/licenses/LICENSE-2.0

package fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.example.moumita.caloriecountergeb.AddFoodActivity;
import com.example.moumita.caloriecountergeb.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import generalpersondatabase.Person;
import generalpersondatabase.PersonDBHandler;
import generalpersondatabase.PersonOperations;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private FitChart fitChart;
    private Button addButton;
    private PersonOperations personData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        /*FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_food_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                //Intent intent = new Intent(HomeFragment.this, NewMessageActivity.class);
                //startActivity(intent);
            }
        });
        */

        personData = new PersonOperations(getContext());
        personData.open();

        Person person = new Person();
        person = personData.getPerson(1);

        personData.close();
        String cal_needed = person.getBMRWithActivity();

        fitChart = view.findViewById(R.id.fitChart);

        fitChart.setMinValue(0f);
        fitChart.setMaxValue(Float.parseFloat(cal_needed));


        addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources resources = getResources();
                Collection<FitChartValue> values = new ArrayList<>();
                values.add(new FitChartValue(30f, resources.getColor(R.color.chart_value_1)));
                values.add(new FitChartValue(20f, resources.getColor(R.color.chart_value_2)));
                values.add(new FitChartValue(15f, resources.getColor(R.color.chart_value_3)));
                values.add(new FitChartValue(10f, resources.getColor(R.color.chart_value_4)));
                fitChart.setValues(values);
            }
        });



        FabSpeedDial fabAddFood = (FabSpeedDial) view.findViewById(R.id.fab_add_food);
        fabAddFood.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return true;
            }
        });
        fabAddFood.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                //TODO: Start some activity
                Intent intent = new Intent(getContext(), AddFoodActivity.class);
                startActivity(intent);
                return false;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}
