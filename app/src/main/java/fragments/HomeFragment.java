//fabs added from: https://github.com/yavski/fab-speed-dial
// license to be included: http://www.apache.org/licenses/LICENSE-2.0

package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.moumita.caloriecountergeb.AddFoodActivity;
import com.example.moumita.caloriecountergeb.R;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }





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
