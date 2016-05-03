package com.example.varsha.weatherforecast;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hamweather.aeris.communication.AerisEngine;

public class map extends Activity
        implements MapFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        Double latitude = extras.getDouble("latitude");
        Double longitude = extras.getDouble("longitude");
        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), this);

        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //add a fragment
        MapFragment myFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);
        myFragment.setArguments(args);
        fragmentTransaction.add(R.id.MapFrame, myFragment);
        fragmentTransaction.commit();
    }
    public void onFragmentInteraction(Uri uri)
    {

    }

}


