package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("created", "Created Activity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("started", "Started Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("resumed","Resumed to Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("paused", "Paused Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("stopped","Stopped Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("destroyed", "Destroyed Activity");
    }
}