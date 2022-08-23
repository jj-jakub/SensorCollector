package com.jj.sensors.framework.managers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.sensors.general.SmartSensorManager
import com.jj.domain.sensors.model.SensorData
import com.jj.core.domain.sensors.interfaces.GPSManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AndroidGPSManager(
    private val context: Context,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : GPSManager, SmartSensorManager<SensorData>() {

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
        coroutineScopeProvider.getIOScope().launch {
            start()
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun onActive(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var registered: Boolean
        withContext(coroutineScopeProvider.main) {
            registered = try {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
                true
            } catch (e: Exception) {
                val error = SensorData.Error(
                    SensorData.ErrorType.InitializationFailure(
                        "Error occurred during GPS listener registration"
                    ), e
                )
                sensorSamples.tryEmit(error)
                false
            }
        }
        return registered
    }

    override fun onInactive() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(listener)
    }
}