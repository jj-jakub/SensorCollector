package com.jj.sensorcollector.framework.sensors

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.jj.sensorcollector.data.sensors.GPSDataCollector
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData
import com.jj.sensorcollector.domain.sensors.interfaces.GPSManager

class AndroidGPSManager(
        private val context: Context,
        private val gpsDataCollector: GPSDataCollector
) : GPSManager {

    private val listener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            gpsDataCollector.onDataReceived(GPSData(location.latitude, location.longitude))
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//            locationInfoUpdates.tryEmit(LocationListenerResult.OnStatusChanged(provider, status, extras))
        }

        override fun onProviderEnabled(provider: String) {
//            logAndPingServer("onProviderEnabled, provider: $provider", tag)
//            locationInfoUpdates.tryEmit(LocationListenerResult.OnProviderEnabled(provider))
        }

        override fun onProviderDisabled(provider: String) {
//            logAndPingServer("onProviderDisabled, provider: $provider", tag)
//            locationInfoUpdates.tryEmit(LocationListenerResult.OnProviderDisabled(provider))
        }
    }

    @SuppressLint("MissingPermission")
    override fun start() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
    }

    override fun stop() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(listener)
    }
}