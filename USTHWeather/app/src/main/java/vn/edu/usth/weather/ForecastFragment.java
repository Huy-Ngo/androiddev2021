package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    private static final String ARG_PARAM1 = "bgColor";

    private String mBgColor;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bgColor Parameter 1.
     * @return A new instance of fragment ForecastFragment.
     */
    public static ForecastFragment newInstance(String bgColor) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBgColor = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        int color;
        switch (mBgColor) {
            case "red":
                color = 0x90FF0000;
                break;
            case "green":
                color = 0x9000FF00;
                break;
            case "blue":
                color = 0x900000FF;
                break;
            default:
                color = 0x90AAAAAA;
                break;
        }
        view.setBackgroundColor(color);
        return view;
    }
}