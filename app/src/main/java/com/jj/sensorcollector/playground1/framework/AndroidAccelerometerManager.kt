package com.jj.sensorcollector.playground1.framework

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.AccelerometerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

sealed class SensorData {
    data class AccSample(val x: Float?, val y: Float?, val z: Float?): SensorData()
    data class Error(val msg: String, val e: Exception?): SensorData()
}

class AndroidAccelerometerManager(
    private val context: Context
) : AccelerometerManager {

    private val _accelerometerSamples = BufferedMutableSharedFlow<SensorData>()

    private var sensorManager: SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    private val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private suspend fun StateFlow<Int>.actdisact(onActive: () -> Unit, onInActive: () -> Unit) {
        coroutineScope {
            this@actdisact.map { count -> count > 0 }
                .distinctUntilChanged()
                .onEach { isActive ->
                    if (isActive) onActive()
                    else onInActive()
                }.launchIn(this)
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _accelerometerSamples.subscriptionCount.actdisact(
                onActive = { onActive() },
                onInActive = { onInactive() }
            )
        }
    }

    private fun onActive() {
        Log.d("ABABX", "onActive")
        if (sensorManager == null) initializeSensorManager()
        sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    private fun onInactive() {
        Log.d("ABABX", "onInActive")
        if (sensorManager == null) initializeSensorManager()
        sensorManager?.unregisterListener(sensorListener, sensor)
    }

    override fun collectAccelerometerSamples(): Flow<SensorData> = _accelerometerSamples.asSharedFlow()

    private fun initializeSensorManager() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        if (sensorManager == null) {
            val sensorData = SensorData.Error("Failed to initialize sensorManager", null)
            _accelerometerSamples.tryEmit(sensorData)
        }
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            Log.d("ABAB", "p0: $p0")
            val sensorData = SensorData.AccSample(p0?.values?.first(), p0?.values?.get(1), p0?.values?.get(2))
            _accelerometerSamples.tryEmit(sensorData)
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            /* no-op */
        }
    }
}