package com.jj.sensorcollector.playground1.framework.data.managers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.jj.sensorcollector.playground1.domain.managers.GPSManager
import com.jj.sensorcollector.playground1.domain.managers.SmartSensorManager
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AndroidGPSManager(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO) // TODO Inject
) : GPSManager, SmartSensorManager() {


    private val listener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            sensorSamples.tryEmit(SensorData.GPSSample(latitude = location.latitude, longitude = location.longitude))
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

    init {
        scope.launch {
            start()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        scope.launch {
            withContext(Dispatchers.Main) {
                try {
                    locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
                } catch (e: Exception) {
                    val error = SensorData.Error(SensorData.ErrorType.InitializationFailure(
                        "Error occurred during GPS listener registration"), e)
                    sensorSamples.tryEmit(error)
                }
            }
        }
    }

    override fun onInactive() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(listener)
    }
}