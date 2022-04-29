package com.jj.sensorcollector.playground1.framework.domain.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AndroidSmartSensorManagerTest {

    @MockK
    private lateinit var context: Context

    @MockK
    private lateinit var sensorManager: SensorManager

    @MockK
    private lateinit var sensor: Sensor

    private lateinit var androidSmartSensorManager: AndroidSmartSensorManager

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        mockLogger()
        every { context.getSystemService(any()) } returns sensorManager
        every { sensorManager.getDefaultSensor(any()) } returns sensor
        androidSmartSensorManager = object : AndroidSmartSensorManager(context, 1, TestCoroutineScope()) {
            override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData {
                return SensorData.AccSample(1f, 1f, 1f)
            }
        }
    }

    @Test
    fun a() {

    }

    private fun mockLogger() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }
}