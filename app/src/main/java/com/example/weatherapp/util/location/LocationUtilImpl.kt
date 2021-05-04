package com.example.weatherapp.util.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.weatherapp.data.LocationDataType

class LocationUtilImpl(private val context: Context) : ILocationUtil {

    private var lastLocation: Location? = null

    override fun getLocationData(): LocationDataType? {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return LocationDataType.DefaultLocation
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)


        if (lastKnownLocation != null
            && lastKnownLocation.latitude != 0.0
            && lastKnownLocation.longitude != 0.0) {
            val locationData = LocationDataType.CurrentLocation
            locationData.lat = lastKnownLocation.latitude ?: 0.0
            locationData.long = lastKnownLocation.longitude ?: 0.0
            lastLocation = lastKnownLocation
            return locationData
        } else {
            if (lastLocation == null) return LocationDataType.DefaultLocation

            val locationData = LocationDataType.ApproximateLocation
            locationData.lat = lastLocation?.latitude ?: 0.0
            locationData.long = lastLocation?.longitude ?: 0.0
            return locationData
        }
    }
}