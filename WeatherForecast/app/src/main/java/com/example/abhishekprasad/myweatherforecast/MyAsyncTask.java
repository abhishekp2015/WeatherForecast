package com.example.abhishekprasad.myweatherforecast;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by ABHISHEK PRASAD on 7/12/2015.
 */
public class MyAsyncTask extends AsyncTask<String, Void, String> {

Context context;
    String City;
    String State;
    String degreevalue;
    public MyAsyncTask(Context context,String[] params){
        this.context=context;
        City=params[0];
        State=params[1];
        degreevalue=params[2];
        Log.d("check1",City);
    }
    @Override
    protected String doInBackground(String... urls) {
        try {
            return Fetchdata(urls[0]);
        } catch (IOException e) {
            return null;
        }

    }

    private String Fetchdata(String myurl) throws IOException {
        InputStream is = null;

        try {

            URL url = new URL(myurl.replace(" ", "%20"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            Integer response = conn.getResponseCode();
  //          Log.d("check", myurl);
            try {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                String contentAsString = readStream(in);
                // Convert the InputStream into a string

//                Log.d("check", contentAsString);
                return contentAsString;
            } finally {
                conn.disconnect();
            }

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result!=null) {

            Intent intent=new Intent(context, ResultActivity.class);
            intent.putExtra("city",City);
            intent.putExtra("state",State);
            intent.putExtra("degreevalue",degreevalue);

            intent.putExtra("Json_string_data", result);
            context.startActivity(intent);

        }
    else{
            Toast.makeText(context,
                    "Data is Empty !!", Toast.LENGTH_SHORT).show();
        }

    }


}
