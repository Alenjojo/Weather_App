package com.example.weatherforecast

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.R.string.cancel
import android.content.DialogInterface
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import android.widget.Toast
import android.location.LocationManager
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.core.content.ContextCompat.getSystemService
import android.R
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class ConnectionManager {

    fun checkConnectivity(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork?.isConnected != null){
            return activeNetwork.isConnected
        } else {
            return false
        }
    }
}


