package vn.edu.usth.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public MainViewPagerAdapter(Context _context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = _context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new WeatherAndForecastFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putString("city", context.getString(R.string.hanoi));
                break;
            case 1:
                args.putString("city", context.getString(R.string.paris));
                break;
            case 2:
                args.putString("city", context.getString(R.string.toulouse));
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
            case 0: return context.getString(R.string.hanoi_full);
            case 1: return context.getString(R.string.paris_full);
            case 2: return context.getString(R.string.toulouse_full);

            default: return "Middle of nowhere";
        }
    }
}
