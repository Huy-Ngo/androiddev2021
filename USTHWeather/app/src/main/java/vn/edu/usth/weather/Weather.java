package vn.edu.usth.weather;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class Weather {
    String description;
    String city;
    int icon;
    double temperature;

    public Weather(Context ctx, JSONObject res) {
        try {
            description = res.getJSONArray("weather").getJSONObject(0)
                .getString("description");
            city = res.getString("name");
            String iconId = res.getJSONArray("weather").getJSONObject(0)
                .getString("icon");
            switch (iconId) {
                case "01d":
                    icon = R.drawable.sun;
                    break;
                case "01n":
                    icon = R.drawable.night;
                    break;
                case "02d":
                    icon = R.drawable.cloud;
                    break;
                case "02n":
                    icon = R.drawable.cloudy_night;
                    break;
                case "03d":
                case "03n":
                    icon = R.drawable.cloud_1;
                    break;
                case "04d":
                case "04n":
                    icon = R.drawable.cloud_6;
                    break;
                case "09d":
                case "09n":
                case "10d":
                case "10n":
                    icon = R.drawable.rain_17;
                    break;
                case "11d":
                case "11n":
                    icon = R.drawable.storm_12;
                    break;
                case "13d":
                case "13n":
                    icon = R.drawable.snowflake_1;
                    break;
                default:
                    icon = R.drawable.cloud_1;
                    Log.e("Icon Error", "Icon not available: " + iconId);
            }
            temperature = res.getJSONObject("main").getDouble("temp");
        } catch (JSONException e) {
            Log.e("JSON", "Failed to parse Weather JSON: " + e);
        }
    }
}
