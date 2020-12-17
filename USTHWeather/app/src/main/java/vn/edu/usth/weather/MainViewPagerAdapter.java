package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new WeatherAndForecastFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putString("city", "Hanoi");
                break;
            case 1:
                args.putString("city", "Paris");
                break;
            case 2:
                args.putString("city", "Toulouse");
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Hanoi, Vietnam";
            case 1: return "Paris, France";
            case 2: return "Toulouse, France";

            default: return "Middle of nowhere";
        }
    }
}
