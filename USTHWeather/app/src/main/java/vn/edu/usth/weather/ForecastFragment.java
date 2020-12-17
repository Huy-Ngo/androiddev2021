package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ForecastFragment.
     */
    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        int color = 0x90ADD8E6;
        view.setBackgroundColor(color);

        LinearLayout mainLayout = view.findViewById(R.id.fragment_forecast);

        int[] days = {R.string.monday_short, R.string.tuesday_short, R.string.wednesday_short,
                R.string.thursday_short, R.string.friday_short, R.string.saturday_short, R.string.sunday_short};
        String[] weathers = {"sun", "cloud", "rain", "hail", "storm"};

        for (int day : days) {
            LinearLayout layout = new LinearLayout(this.getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1
            );
            layout.setLayoutParams(params);

            TextView dayText = new TextView(this.getContext());
            dayText.setText(day);
            dayText.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams dayTextParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1
            );
            dayText.setLayoutParams(dayTextParams);

            Random random = new Random(System.currentTimeMillis());
            int weatherId = random.nextInt(weathers.length);
            String weather = weathers[weatherId];

            ImageView weatherIcon = new ImageView(this.getContext());
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1
            );
            weatherIcon.setLayoutParams(iconParams);
            weatherIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            TextView weatherDescription = new TextView(this.getContext());
            weatherDescription.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 3
            );
            weatherDescription.setLayoutParams(descriptionParams);

            switch (weather) {
                case "sun":
                    weatherIcon.setImageResource(R.drawable.sun);
                    weatherDescription.setText(R.string.sun);
                    break;
                case "rain":
                    weatherIcon.setImageResource(R.drawable.rain);
                    weatherDescription.setText(R.string.rain);
                    break;
                case "hail":
                    weatherIcon.setImageResource(R.drawable.hail);
                    weatherDescription.setText(R.string.hail);
                    break;
                case "storm":
                    weatherIcon.setImageResource(R.drawable.storm);
                    weatherDescription.setText(R.string.storm);
                    break;
                case "cloud":
                    weatherIcon.setImageResource(R.drawable.cloud);
                    weatherDescription.setText(R.string.cloud);
                    break;
            }

            layout.addView(dayText);
            layout.addView(weatherIcon);
            layout.addView(weatherDescription);

            mainLayout.addView(layout);
        }

        return view;
    }
}