package com.example.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.btl.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: navigationView.getMenu().findItem(R.id.Home).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.Search).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.Cart).setChecked(true);
                        break;
                    case 3: navigationView.getMenu().findItem(R.id.Bill).setChecked(true);
                        break;
                    case 4: navigationView.getMenu().findItem(R.id.Account).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home: viewPager.setCurrentItem(0);
                        break;
                    case R.id.Search: viewPager.setCurrentItem(1);
                        break;
                    case R.id.Cart: viewPager.setCurrentItem(2);
                        break;
                    case R.id.Bill: viewPager.setCurrentItem(3);
                        break;
                    case R.id.Account: viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }
}