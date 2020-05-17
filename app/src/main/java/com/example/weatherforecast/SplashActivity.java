package com.example.weatherforecast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    Animation topanim, bottomanim;
    ImageView image;
    TextView sub;
    String a="1",city1,state1;

    private static int SPLASH_SCREEN = 3000;

    private static final int REQUEST_LOCATION = 1;
      LocationManager locationManager;
    Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        topanim = AnimationUtils.loadAnimation(this, R.anim.fromtop);
       // bottomanim = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        image = findViewById(R.id.imgballon);
       // sub = findViewById(R.id.btnsub);

        image.setAnimation(topanim);
        //sub.setAnimation(bottomanim);


        //Add permission

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

               //showLocationTxt=findViewById(R.id.txxt1);


       if(a=="1") {


           locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

           //Check gps is enable or not

           if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
               //Write Function To enable gps

               OnGPS();
           } else {
               //GPS is already On then

               getLocation();
           }
       }




    }

    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double longi = LocationGps.getLongitude();


                latitude =lat;
                longitude =longi;
locat(LocationNetwork);
               // showLocationTxt.setText("Latitude= " + latitude + "\n" + "Longitude= " + longitude);
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude =lat;
                longitude =longi;
                locat(LocationNetwork);
               // showLocationTxt.setText("Latitude= " + latitude + "\n" + "Longitude= " + longitude);
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude =lat;
                longitude =longi;
                locat(LocationNetwork);
               // showLocationTxt.setText("Latitude= " + latitude + "\n" + "Longitude= " + longitude);
            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

        }

    }


    private void OnGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
              finish();
            }
        }).setNegativeButton("Enter Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("city","");
                intent.putExtra("state","");
                startActivity(intent);
                finish();
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void locat(Location location){

        try {
            Geocoder geocoder=new Geocoder(this);
            List<Address> addresses=null;
            addresses=geocoder.getFromLocation(latitude,longitude,1);
            String country=addresses.get(0).getCountryName();
             city1=addresses.get(0).getLocality();
             state1=addresses.get(0).getLocality();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_SHORT).show();
        }

          new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("city",city1);
                intent.putExtra("state",state1);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}
