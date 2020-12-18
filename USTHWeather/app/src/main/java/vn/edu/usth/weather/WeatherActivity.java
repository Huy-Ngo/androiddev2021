package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WeatherActivity extends AppCompatActivity {
    MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString("bgColor", "blue");
        forecastFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(
                R.id.main_pager, forecastFragment).commit();

        PagerAdapter adapter = new MainViewPagerAdapter(
                this.getApplicationContext(),
                getSupportFragmentManager()
        );
        ViewPager pager = findViewById(R.id.main_pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.main_tab);
        tabLayout.setupWithViewPager(pager);
        Log.i("created", "Created Activity");
    }

    @Override
    protected void onStart() {
        super.onStart();

        InputStream inputStream = this.getApplicationContext().getResources()
                .openRawResource(R.raw.labyrinth);
        
        byte[] buffer = new byte[7000000];

        File sdCard = this.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File musicFile = new File(sdCard, "Labyrinth.mp3");

        try {
            OutputStream outputStream = new FileOutputStream(musicFile);
            int length = inputStream.read(buffer);
            outputStream.write(buffer, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("inserted", "Added song to path: " + musicFile.getAbsolutePath());

        musicPlayer = MediaPlayer.create(this, R.raw.labyrinth);
        musicPlayer.start();

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