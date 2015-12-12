package com.example.abhishekprasad.myweatherforecast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DetailsActivity extends Activity {

    LinearLayout ll;
    LayoutInflater layoutInflater;
    LinearLayout ll2;
    String city = null;
    String state = null;
    String degreevalue=null;
    String icon = null;
    int src;
    JSONArray data;
    JSONArray dailydata;
    String[] weekdays = new String[7];

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
         ll=(LinearLayout) this.findViewById(R.id.viewsContainer);
        ll2=(LinearLayout) this.findViewById(R.id.next7days);

        Button plus=(Button) findViewById(R.id.button8);
        final Button next=(Button) findViewById(R.id.button7);
        final Button next24=(Button) findViewById(R.id.button6);
        next24.setBackgroundColor(Color.parseColor("#0080FF"));
        weekdays[0]=  "Sunday";
        weekdays[1] = "Monday";
        weekdays[2] = "Tuesday";
        weekdays[3] = "Wednesday";
        weekdays[4] = "Thursday";
        weekdays[5] = "Friday";
        weekdays[6] = "Saturday";
        String symbol=null;


        final Bundle extras = getIntent().getExtras();
               if (extras != null) {
            String JsonString = extras.getString("Json_string_data");
            city = extras.getString("city");
            state = extras.getString("state");
            degreevalue = extras.getString("degreevalue");

            JSONObject jsonObj = null;
                   if ((degreevalue).equals("si")) {
                       symbol = "C";
                   } else {
                       symbol="F";
                   }
                   TextView tmp=(TextView)findViewById(R.id.textView3);
                   tmp.setText("Temp("+"°"+symbol+")");
                   TextView top1=(TextView)findViewById(R.id.textView11);
                   top1.setText("More Details for "+city+", "+state);
                   top1.setTypeface(null, Typeface.BOLD);


            try {
                jsonObj = new JSONObject(JsonString);
                JSONObject hourly = jsonObj.getJSONObject("hourly");
                JSONObject daily=jsonObj.getJSONObject("daily");
                data = hourly.getJSONArray("data");
                dailydata=daily.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            layoutInflater = (LayoutInflater)
                    this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (Integer i = 0; i < 24; i++) {
                try {
                    icon = data.getJSONObject(i).getString("icon").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                String timezone = null;
                try {
                    timezone = jsonObj.getString("timezone");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                df.setTimeZone(TimeZone.getTimeZone(timezone));
                Long time1 = null;
                try {
                    time1 = Long.parseLong(data.getJSONObject(i).getString("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Date d = new Date(time1 * 1000L);
                String time = df.format(d);
                View inflatedView = layoutInflater.inflate(R.layout.mylayout, null, false);
                if (i % 2 == 0) {
                    inflatedView.setBackgroundColor(Color.parseColor("#CBCBCB"));
                } else {
                    inflatedView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                Integer t1 = null;
                try {
                    t1 = Math.round(Float.parseFloat(data.getJSONObject(i).getString("temperature")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String temperatureText = t1.toString();
                TextView t = (TextView) inflatedView.findViewById(R.id.textView1);
                t.setText(time);
                ImageView imageIcon=(ImageView)inflatedView.findViewById(R.id.imageView4);
                imageIcon.setImageResource(src);
                TextView temperature = (TextView) inflatedView.findViewById(R.id.textView3);
                temperature.setText(temperatureText);
                ll.addView(inflatedView, i);
            }

            for (Integer i = 0; i <7; i++) {
                String[] color=new String[7];
                color[0]="#EF4220";
                color[1]="#E88E48";
                color[2]="#A7A52E";
                color[3]="#986EA8";
                color[4]="#F57B7C";
                color[5]="#D04470";
                color[6]="#327CB7";
                try {
                    icon = dailydata.getJSONObject(i+1).getString("icon").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                String timezone = null;
                try {
                    timezone = jsonObj.getString("timezone");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd");
                df.setTimeZone(TimeZone.getTimeZone(timezone));
                Long time1 = null;
                try {
                    time1 = Long.parseLong(dailydata.getJSONObject(i+1).getString("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Date d = new Date(time1 * 1000L);
                String time = df.format(d);
                View inflatedView1 = layoutInflater.inflate(R.layout.next7days, null, false);
                String Temperaturemin = null;
                String Temperaturemax=null;
                try {
                    Integer t = Math.round(Float.parseFloat(dailydata.getJSONObject(i + 1).getString("temperatureMin")));
                    Temperaturemin = t.toString();
                     t = Math.round(Float.parseFloat(dailydata.getJSONObject(i + 1).getString("temperatureMax")));
                    Temperaturemax = t.toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((degreevalue).equals("si")) {
                    symbol = "C";
                } else {
                    symbol="F";
                }
                TextView t = (TextView) inflatedView1.findViewById(R.id.textView13);
                t.setText(("L:" + Temperaturemin + "\u00B0"+symbol + " | " + "H:" + Temperaturemax + "\u00B0"+symbol));
                ImageView imageIcon=(ImageView)inflatedView1.findViewById(R.id.imageView3);
                     imageIcon.setImageResource(src);
                TextView daytext=(TextView)inflatedView1.findViewById(R.id.textView12);
                daytext.setText(time);
                inflatedView1.setBackgroundColor(Color.parseColor(color[i]));
                ll2.addView(inflatedView1, i);
                 }
                ll2.setVisibility(View.GONE);

            final JSONObject finalJsonObj = jsonObj;
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout plusbutton = (LinearLayout) findViewById(R.id.plusbutton);
                    plusbutton.setVisibility(View.GONE);
                    for (Integer i = 25; i < 48; i++) {
                        try {
                            icon = data.getJSONObject(i).getString("icon").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                        String timezone = null;
                        try {
                            timezone = finalJsonObj.getString("timezone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                        df.setTimeZone(TimeZone.getTimeZone(timezone));
                        Long time1 = null;
                        try {
                            time1 = Long.parseLong(data.getJSONObject(i).getString("time"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Date d = new Date(time1 * 1000L);
                        String time = df.format(d);
                        View inflatedView = layoutInflater.inflate(R.layout.mylayout, null, false);
                        if (i % 2 == 0) {
                            inflatedView.setBackgroundColor(Color.parseColor("#CBCBCB"));
                        } else {
                            inflatedView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                        Integer t1 = null;
                        try {
                            t1 = Math.round(Float.parseFloat(data.getJSONObject(i).getString("temperature")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String temperatureText = t1.toString();
                        TextView t = (TextView) inflatedView.findViewById(R.id.textView1);
                        t.setText(time);
                        TextView temperature = (TextView) inflatedView.findViewById(R.id.textView3);
                        temperature.setText(temperatureText);
                        ImageView imageIcon=(ImageView)inflatedView.findViewById(R.id.imageView4);
                        imageIcon.setImageResource(src);

                        ll.addView(inflatedView, i);
                    }

                }
            });
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setBackgroundColor(Color.parseColor("#0080FF"));
                next24.setBackgroundColor(Color.parseColor("#CBCBCB"));
                ll.setVisibility(View.GONE);
                ll2.setVisibility(View.VISIBLE);
               LinearLayout mylayout1=(LinearLayout)findViewById(R.id.mylayout1);
                mylayout1.setVisibility(View.GONE);
            }
        });
        next24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next24.setBackgroundColor(Color.parseColor("#0080FF"));
                next.setBackgroundColor(Color.parseColor("#CBCBCB"));
                LinearLayout mylayout1=(LinearLayout)findViewById(R.id.mylayout1);
                mylayout1.setVisibility(View.VISIBLE);
                ll.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);

            }
        });

    }


}
