package com.example.abhishekprasad.myweatherforecast;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends Activity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        ImageView imageIcon = (ImageView) findViewById(R.id.imageView);
        ImageButton fb_button = (ImageButton) findViewById(R.id.imageButton);
        TextView weather = (TextView) findViewById(R.id.textView7);
        final TextView temperature = (TextView) findViewById(R.id.textView8);
        TextView temperatureRange = (TextView) findViewById(R.id.textView9);
        Button mapbutton = (Button) findViewById(R.id.button4);
        Button moredetails=(Button) findViewById(R.id.button3);
        String icon = null;
        String summary = null;
        String temperatureText = null;
        String temperatureMin = null;
        String temperatureMax = null;

        String precipProbability = null;
        String windSpeed = null;
        String visibility = null;
        String dewPoint = null;
        String humidity = null;
        String sunriseTime = null;
        String sunsetTime = null;
        String city = null;
        String state = null;
        String lat = null;
        String lng = null;
        String Precipitation = null;
        float PrecipIntensity = 0;
        String degreevalue = null;

        int src;
        final Bundle extras = getIntent().getExtras();
        String symbol = null;
        if (extras != null) {
            String JsonString = extras.getString("Json_string_data");
            city = extras.getString("city");
            state = extras.getString("state");
            degreevalue = extras.getString("degreevalue");

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(JsonString);

                JSONObject currently = jsonObj.getJSONObject("currently");
                JSONObject daily = jsonObj.getJSONObject("daily");
                JSONArray data = daily.getJSONArray("data");
                summary = currently.getString("summary").toString();
                icon = currently.getString("icon").toString();
                lat = jsonObj.getString("latitude").toString();
                lng = jsonObj.getString("longitude").toString();
                PrecipIntensity = Float.parseFloat(currently.getString("precipIntensity"));
                Integer t = Math.round(Float.parseFloat(currently.getString("temperature")));
                temperatureText = t.toString();
                t = Math.round(Float.parseFloat(data.getJSONObject(0).getString("temperatureMin")));
                temperatureMin = t.toString();
                t = Math.round(Float.parseFloat(data.getJSONObject(0).getString("temperatureMax")));
                temperatureMax = t.toString();
                symbol = null;
                windSpeed = currently.getString("windSpeed").toString();
                visibility = currently.getString("visibility").toString();
                dewPoint = currently.getString("dewPoint").toString();
                precipProbability = String.valueOf(Math.round(Float.parseFloat(currently.getString("precipProbability")) * 100)) + "%";
                humidity = String.valueOf(Math.round(Float.parseFloat(currently.getString("humidity")) * 100)) + "%";
                String timezone = jsonObj.getString("timezone");
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                df.setTimeZone(TimeZone.getTimeZone(timezone));
                Long time1 = Long.parseLong(data.getJSONObject(0).getString("sunsetTime"));
                Date d = new Date(time1 * 1000L);
                sunsetTime = df.format(d);
                Long time2 = Long.parseLong(data.getJSONObject(0).getString("sunriseTime"));
                d = new Date(time2 * 1000L);
                sunriseTime = df.format(d);
                if ((degreevalue).equals("si")) {

                    symbol = "C";
                    windSpeed = windSpeed + " " + "m/s";
                    PrecipIntensity = (float) (Math.round(PrecipIntensity * 0.039 * 1000.0) / 1000.0);
                    Log.d("find", String.valueOf(PrecipIntensity));
                    visibility = visibility + " " + "km";
                    dewPoint = dewPoint + "°" + symbol;
                } else {
                    symbol = "F";
                    windSpeed = windSpeed + " " + "mph";
                    visibility = visibility + " " + "mi";
                    dewPoint = dewPoint + "°" + symbol;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            weather.setText(summary + " in " + city + ", " + state);
            temperature.setText(temperatureText);
            temperatureRange.setText("L:" + temperatureMin + "\u00B0" + " | " + "H:" + temperatureMax + "\u00B0");
            switch (icon) {
                case "clear-day":
                    src = R.drawable.clear;
                    break;
                case "clear-night":
                    src = R.drawable.clear_night;
                    break;
                case "rain":
                    src = R.drawable.rain;
                    break;
                case "snow":
                    src = R.drawable.snow;
                    break;
                case "sleet":
                    src = R.drawable.sleet;
                    break;
                case "wind":
                    src = R.drawable.wind;
                    break;
                case "fog":
                    src = R.drawable.fog;
                    break;
                case "cloudy":
                    src = R.drawable.cloudy;
                    break;
                case "partly-cloudy-day":
                    src = R.drawable.cloud_day;
                    break;
                case "partly-cloudy-night":
                    src = R.drawable.cloud_night;
                    break;
                default:
                    src = R.drawable.clear;
            }
            imageIcon.setImageResource(src);
        }
        if (PrecipIntensity >= 0 && PrecipIntensity < 0.002) {
            Precipitation = "None";
        } else if (PrecipIntensity >= 0.002 && PrecipIntensity < 0.017) {
            Precipitation = "Very Light";
        } else if (PrecipIntensity >= 0.017 && PrecipIntensity < 0.1) {
            Precipitation = "Light";
        } else if (PrecipIntensity >= 0.1 && PrecipIntensity < 0.4) {
            Precipitation = "Moderate";
        } else if (PrecipIntensity >= 0.4) {
            Precipitation = "Heavy";
        } else {
            Precipitation = "None";
        }


        TextView precipitationValue = (TextView) findViewById(R.id.precipitationValue);
        TextView rainValue = (TextView) findViewById(R.id.rainValue);
        TextView windValue = (TextView) findViewById(R.id.windValue);
        TextView dewValue = (TextView) findViewById(R.id.dewValue);
        TextView humidityValue = (TextView) findViewById(R.id.humidityValue);
        TextView visibilityValue = (TextView) findViewById(R.id.visibilityValue);
        TextView riseValue = (TextView) findViewById(R.id.riseValue);
        TextView setValue = (TextView) findViewById(R.id.setValue);
        TextView symb = (TextView) findViewById(R.id.textView10);
        symb.setText("°"+symbol);
        precipitationValue.setText(Precipitation);
        rainValue.setText(precipProbability);
        windValue.setText(windSpeed);
        dewValue.setText(dewPoint);
        humidityValue.setText(humidity);
        visibilityValue.setText(visibility);
        riseValue.setText(sunriseTime);
        setValue.setText(sunsetTime);

        String imgURLpath = "http://cs-server.usc.edu:45678/hw/hw8/images/";

        if (icon.equals("clear-day"))
            imgURLpath += "clear.png";
        else if (icon.equals("clear-night"))
            imgURLpath += "clear_night.png";
        else if (icon.equals("partly-cloudy-day"))
            imgURLpath += "cloud_day.png";
        else if (icon.equals("partly-cloudy-night"))
            imgURLpath += "cloud_night.png";
        else
            imgURLpath += icon + ".png";

        final String imgURL = imgURLpath;
        final String finalCity = city;
        final String finalState = state;
        final String finalIcon = icon;
        final String finalTemperatureText = temperatureText;
        fb_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + finalCity + "," + finalState)
                            .setContentDescription(finalIcon.replace("-", " ") + ", " + finalTemperatureText + " \u00B0F")
                            .setContentUrl(Uri.parse("http://www.forecast.io")).setImageUrl(Uri.parse(imgURL))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });

        final String finalLat = lat;
        final String finalLng = lng;
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MapActivity.class);
                intent.putExtra("lat", finalLat);
                intent.putExtra("lng", finalLng);
                startActivity(intent);
            }
        });
        final String finalCity1 = city;
        final String finalState1 = state;
        final String finaldegree1 = degreevalue;

        moredetails.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
            intent.putExtra("city", finalCity1);
            intent.putExtra("state", finalState1);
            intent.putExtra("degreevalue",finaldegree1);
            intent.putExtra("Json_string_data", extras.getString("Json_string_data"));
            startActivity(intent);
        }
    });
    }


}

