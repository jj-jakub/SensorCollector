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
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AndroidSmartSensorManagerTest {

    @MockK
    private lateinit var context: Context

    @MockK
    private lateinit var sensorManager: SensorManager

    @MockK
    private lateinit var sensor: Sensor

    private lateinit var androidSmartSensorManager: AndroidSmartSensorManager

    private lateinit var testCoroutineScope: TestCoroutineScope

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        mockLogger()
        setupMocks()
        testCoroutineScope = TestCoroutineScope()
    }

    @Test
    fun a() {

    }

    @Test
    fun `when class has been initialized, then should call onInactive`() = testCoroutineScope.runBlockingTest {
        setupAndroidSmartSensorManager()
        assertEquals(1, inactiveCalls)
    }

    @Test
    fun `when collecting flow starts, then should call onActive`() = testCoroutineScope.runBlockingTest {
        setupContextMock(sensorManagerReturn = sensorManager)
        setupAndroidSmartSensorManager()

        collectSamples()

        assertEquals(1, activeCalls)
    }

    @Test
    fun `when initial sensorManager is null, then should try to reinitialize it after collecting and calling onActive`() {
        setupContextMock(sensorManagerReturn = null)
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization, 2 -> onInactive call 3 -> onActive call
        verify(exactly = 3) { context.getSystemService(Context.SENSOR_SERVICE) }
    }


    @Test
    fun `when initial sensor is null, then should try to reinitialize it after collecting and calling onActive`() {
        setupGetDefaultSensorAnswer(null)
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization, 2 -> onInactive call 3 -> onActive call
        verify(exactly = 3) { context.getSystemService(Context.SENSOR_SERVICE) }
    }

    private fun collectSamples() {
        testCoroutineScope.launch {
            androidSmartSensorManager.collectRawSensorSamples().collect {}
        }
    }

    private fun mockLogger() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    private fun setupMocks() {
        setupContextMock(sensorManagerReturn = sensorManager)
        setupGetDefaultSensorAnswer(sensor)
        every { sensorManager.getDefaultSensor(any()) } returns sensor
        every { sensorManager.registerListener(any(), any<Sensor>(), any()) } returns true
        every { sensorManager.unregisterListener(any(), any<Sensor>()) } returns Unit
    }

    private fun setupContextMock(sensorManagerReturn: SensorManager?) {
        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManagerReturn
    }

    private fun setupGetDefaultSensorAnswer(sensor: Sensor?) {
        every { sensorManager.getDefaultSensor(any()) } returns sensor
    }

    private var activeCalls = 0
    private var inactiveCalls = 0

    private fun setupAndroidSmartSensorManager() {
        activeCalls = 0
        inactiveCalls = 0

        androidSmartSensorManager = object : AndroidSmartSensorManager(context, 1, testCoroutineScope) {
            override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData {
                return SensorData.AccSample(1f, 1f, 1f)
            }

            override suspend fun onActive(): Boolean {
                activeCalls++
                return super.onActive()
            }

            override fun onInactive() {
                inactiveCalls++
                super.onInactive()
            }
        }
    }
}