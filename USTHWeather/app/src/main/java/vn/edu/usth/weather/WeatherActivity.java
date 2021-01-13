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

public class WeatherActivity extends AppCompatActivity {
    MediaPlayer musicPlayer;
    AsyncTask<String, Integer, Bitmap> task;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                task = new AsyncTask<String, Integer, Bitmap>() {
                    @Override
                    protected void onPreExecute() {
                        // do nothing
                    }

                    @Override
                    protected Bitmap doInBackground(String... params) {
                        Bitmap logo = null;
                        URL url;
                        HttpURLConnection connection = null;
                        int status = 400;
                        try {
                            url = new URL(params[0]);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            status = connection.getResponseCode();
                        } catch (MalformedURLException e) {
                            Log.e("MalformedURLException", e.getMessage());
                        } catch (IOException e) {
                            Log.e("IOException", e.getMessage());
                        }
                        Log.i("Status", "Connection to USTH server responding: " + status);
                        try {
                            InputStream in = new BufferedInputStream(connection.getInputStream());
                            logo = BitmapFactory.decodeStream(in);
                        } catch (IOException e) {
                            Log.e("IOException", e.getMessage());
                        } finally {
                            connection.disconnect();
                        }
                        return logo;
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        // Do nothing
                    }

                    @Override
                    protected void onPostExecute(Bitmap response) {
                        Log.i("Bitmap", "Received bitmap: " + response);
                        ImageView view = findViewById(R.id.mainlogo);
                        view.setImageBitmap(response);
                        showToast(R.string.refreshed);
                    }
                };
                task.execute("https://usth.edu.vn/uploads/tin-tuc/2019_12/logo-usth-pa3-01.png");
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
