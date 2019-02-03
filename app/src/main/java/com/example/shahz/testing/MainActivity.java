package com.example.shahz.testing;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    AppLocationService mAppLocationService;
    boolean mBounded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.callLocation);
//
//        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
//        pulsator.start();


    }

    public void OpenAddress(View view){
        Location location = mAppLocationService.getLastKnownLocation();
        if (location != null){
            Log.e("location","latitude:"+location.getLatitude()+" longtitude"+location.getLongitude());
        }else {
            Log.e("location","location return null");
        }
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBounded = true;
            AppLocationService.LocalBinder binder = (AppLocationService.LocalBinder) service;
            mAppLocationService = binder.getInstance();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBounded = false;
            mAppLocationService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(MainActivity.this,AppLocationService.class);
        bindService(i,mConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBounded){
            unbindService(mConnection);
            mBounded = false;
        }
    }


}
