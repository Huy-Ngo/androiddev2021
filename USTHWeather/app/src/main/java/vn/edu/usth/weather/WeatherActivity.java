package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.lang.Thread;

import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    MediaPlayer musicPlayer;
    AsyncTask<String, Integer, Bitmap> task;
    RequestQueue queue;
    MainViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString("bgColor", "blue");
        forecastFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(
                R.id.main_pager, forecastFragment).commit();

        adapter = new MainViewPagerAdapter(
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

    private void showToast(int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    private void getLogo() {
        Response.Listener<Bitmap> listener =
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        /*
                        ImageView main = findViewById(R.id.mainlogo);
                        ImageView sub = findViewById(R.id.logo);
                        main.setImageBitmap(response);
                        sub.setImageBitmap(response);
                        */
                        adapter.setLogo(response);
                    }
                };
        ImageRequest imagerequest = new ImageRequest(
                "https://usth.edu.vn/uploads/tin-tuc/2019_12/logo-usth-pa3-01.png",
                listener, 0, 0, ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888, null
        );
        queue.add(imagerequest);
    }

    private void getWeather(String city) {
        Response.Listener<JSONObject> listener =
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Weather weather = new Weather(response);
                        adapter.setWeather(weather);
                    }
                };
        String key = "633f282ec292b27cee1371842180da61";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city;
        url += "appid=" + key;
        url += "units=metric";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url, null, listener
        );
        queue.add(imagerequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getLogo();
                showToast(R.string.refreshed);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this.getApplicationContext(), PrefActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
