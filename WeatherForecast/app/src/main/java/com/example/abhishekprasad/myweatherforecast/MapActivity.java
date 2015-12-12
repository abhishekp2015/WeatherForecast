package com.example.abhishekprasad.myweatherforecast;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapsEngine;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        Bundle bundle = new Bundle();
        bundle.putString("lat", lat);
        bundle.putString("lng", lng);
        Log.d("check11",lat+" "+lng);
        MapFragment map=new MapFragment();
        map.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.map1, map);
        ft.commit();
    }

    public static class MapFragment extends MapViewFragment implements
            OnAerisMapLongClickListener, AerisCallback

    {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String a=this.getString(R.string.aeris_client_id);
            String b=this.getString(R.string.aeris_client_secret);
            Log.d("key",a+" "+b);
            AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "com.example.abhishekprasad.myweatherforecast");
            View view = inflater.inflate(R.layout.fragment_map, container, false);
            mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
            mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);
            mapView.setOnAerisMapLongClickListener(this);
            Bundle bundle = getArguments();
            String lat = bundle.getString("lat");
            String lng = bundle.getString("lng");

            Location location = new Location("");
            location.setLatitude(Double.valueOf(lat));
            location.setLongitude(Double.valueOf(lng));

            mapView.moveToLocation(location,9);
            mapView.addLayer(AerisTile.RADSAT);
            return view;
        }


        @Override
        public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

        }

        @Override
        public void onMapLongClick(double v, double v1) {

        }
    }
}
