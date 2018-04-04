package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moumita.caloriecountergeb.R;


public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 6 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.tab_layout, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return v;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new MoviesFragment();
                case 1 : return new SportsFragment();
                case 2 : return new GamesFragment();
                case 3 : return new PhotographyFragment();
                case 4 : return new WildFragment();
                case 5 : return new TravelFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return getResources().getString(R.string.tab1);
                case 1 :
                    return getResources().getString(R.string.tab2);
                case 2 :
                    return getResources().getString(R.string.tab3);
                case 3 :
                    return getResources().getString(R.string.tab4);
                case 4 :
                    return getResources().getString(R.string.tab5);
                case 5 :
                    return getResources().getString(R.string.tab6);
            }
            return null;
        }
    }

}

