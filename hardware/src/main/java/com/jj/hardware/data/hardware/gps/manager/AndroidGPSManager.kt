package com.jj.hardware.data.hardware.gps.manager

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.SmartSensorManager
import com.jj.domain.hardware.general.model.SensorInitializationResult
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.gps.manager.GPSManager
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
    override suspend fun onActive(): SensorInitializationResult {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var registered: SensorInitializationResult
        withContext(coroutineScopeProvider.main) {
            registered = try {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
                SensorInitializationResult.Success
            } catch (e: Exception) {
                onInitializationError(e)
                SensorInitializationResult.InitializationError(e.message ?: "Error")
            }
        }
        return registered
    }

    private fun onInitializationError(e: Exception) {
        val sensorData = SensorData.Error(SensorData.ErrorType.InitializationFailure("Error occurred during GPS listener registration"), e)
        sensorSamples.tryEmit(sensorData)
    }

    override fun onInactive() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(listener)
    }
}