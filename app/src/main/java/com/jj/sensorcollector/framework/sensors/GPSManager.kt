package com.jj.sensorcollector.framework.sensors

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.jj.sensorcollector.data.sensors.GPSDataCollector
import com.jj.sensorcollector.domain.sensors.ISensorManager
import com.jj.sensorcollector.domain.sensors.SensorData
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData

class GPSManager(
        private val context: Context,
        private val gpsDataCollector: GPSDataCollector
) : ISensorManager<SensorData.GPSData> {

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

    override fun start() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        try {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
        } catch (e: SecurityException) {
            Log.e("ABAB", "Failed to register for GPS updates", e)
        }
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}