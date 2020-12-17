package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherAndForecastFragment extends Fragment {
    private static final String ARG_PARAM1 = "city";
    private String mCity;

    public WeatherAndForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherAndForecastFragment newInstance() {
        WeatherAndForecastFragment fragment = new WeatherAndForecastFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCity = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);
        TextView cityName = view.findViewById(R.id.fragment_weather_city);
        cityName.setText(mCity);

        return view;
    }
}