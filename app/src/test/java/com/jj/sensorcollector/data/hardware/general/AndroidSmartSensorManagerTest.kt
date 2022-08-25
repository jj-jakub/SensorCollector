package com.jj.sensorcollector.data.hardware.general

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import app.cash.turbine.test
import com.jj.domain.hardware.model.SensorData
import com.jj.hardware.data.hardware.general.AndroidSmartSensorManager
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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

        // 1 -> Class instance initialization, 2 -> onActive call (reinitialization)
        verify(exactly = 2) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)
    }

    @Test
    fun `when initial sensorManager is null, then after collecting and reinitialization failure should emit error`() =
        testCoroutineScope.runBlockingTest {
            setupContextMock(sensorManagerReturn = null)
            setupAndroidSmartSensorManager()
            collectSamples()

            androidSmartSensorManager.collectRawSensorSamples().test {

                val sensorError = awaitItem()
                assertTrue(sensorError is SensorData.Error)
                val errorType = (sensorError as SensorData.Error).errorType
                assertTrue(errorType is SensorData.ErrorType.InitializationFailure)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `when initial sensor is null, then should try to reinitialize it after collecting and calling onActive`() {
        setupGetDefaultSensorAnswer(null)
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization, 2 -> onActive call (reinitialization)
        verify(exactly = 2) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)
    }

    @Test
    fun `when initial sensorManager is null and reinitialization failed then isActiveState should remain false`() {
        setupContextMock(sensorManagerReturn = null)
        setupAndroidSmartSensorManager()

        collectSamples()

        // 1 -> Class instance initialization, 2 -> onActive call (reinitialization)
        verify(exactly = 2) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)

        assertFalse(androidSmartSensorManager.collectIsActiveState().value)
    }

    @Test
    fun `when listener registration failed then isActiveState should remain false`() {
        setupListenerRegistrationAnswer(false)
        setupAndroidSmartSensorManager()

        collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)

        assertFalse(androidSmartSensorManager.collectIsActiveState().value)
    }

    @Test
    fun `when listener registration is successful then isActiveState should become true`() {
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)

        assertTrue(androidSmartSensorManager.collectIsActiveState().value)
    }

    @Test
    fun `when listener registration is successful, then second subscription shouldn't call onActive`() {
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }
        collectSamples()

        assertEquals(1, activeCalls)
    }

    @Test
    fun `when listener registration failed, then second subscription should call onActive`() {
        setupListenerRegistrationAnswer(false)
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }
        collectSamples()

        assertEquals(2, activeCalls)
    }

    @Test
    fun `when subscriber stops collecting, then should call onInactive`() {
        setupAndroidSmartSensorManager()
        val collectingJob = collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }

        assertEquals(1, inactiveCalls)
        collectingJob.cancel()
        assertEquals(2, inactiveCalls)
    }

    @Test
    fun `when subscriber stops collecting and onInactive is called, then isActiveState should become false`() {
        setupAndroidSmartSensorManager()
        val collectingJob = collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }

        assertEquals(1, inactiveCalls)
        assertTrue(androidSmartSensorManager.collectIsActiveState().value)
        collectingJob.cancel()
        assertEquals(2, inactiveCalls)
        assertFalse(androidSmartSensorManager.collectIsActiveState().value)
    }

    @Test
    fun `when second subscriber stops collecting, then shouldn't call onInactive`() {
        setupAndroidSmartSensorManager()
        collectSamples()
        val secondCollectingJob = collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }

        assertEquals(1, activeCalls)
        assertEquals(1, inactiveCalls)
        secondCollectingJob.cancel()
        assertEquals(1, inactiveCalls)
    }

    @Test
    fun `when both subscribers stops collecting, then should call onInactive`() {
        setupAndroidSmartSensorManager()
        val collectingJob = collectSamples()
        val secondCollectingJob = collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }

        assertEquals(1, activeCalls)
        assertEquals(1, inactiveCalls)
        collectingJob.cancel()
        secondCollectingJob.cancel()
        assertEquals(2, inactiveCalls)
    }

    @Test
    fun `when reinitialization failed, then second subscriber should make onActive call`() {
        setupContextMock(sensorManagerReturn = null)
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization, 2 -> onActive call (reinitialization)
        verify(exactly = 2) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)

        collectSamples()

        // 1 -> Class instance initialization, 2-3 -> onActive call (reinitialization)
        verify(exactly = 3) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(2, activeCalls)
    }

    @Test
    fun `when registration failed, then second subscriber should make onActive call`() {
        setupListenerRegistrationAnswer(false)
        setupAndroidSmartSensorManager()
        collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }
        assertEquals(1, activeCalls)

        collectSamples()

        assertEquals(2, activeCalls)
    }

    @Test
    fun `when onSensorChanged is called, then should emit sensorData to subscribers`() =
        testCoroutineScope.runBlockingTest {
            val listenerCapture = slot<SensorEventListener>()
            every { sensorManager.registerListener(capture(listenerCapture), any(), any()) } returns true

            setupAndroidSmartSensorManager()

            androidSmartSensorManager.collectRawSensorSamples().test {
                listenerCapture.captured.onSensorChanged(mockk(relaxed = true))
                awaitItem()
            }
        }

    @Test
    fun `when subscriber stops collecting and onInactive is called, then should unregister listener`() {
        setupAndroidSmartSensorManager()
        val collectingJob = collectSamples()

        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }

        assertEquals(1, inactiveCalls)
        assertTrue(androidSmartSensorManager.collectIsActiveState().value)
        collectingJob.cancel()
        assertEquals(2, inactiveCalls)
        assertFalse(androidSmartSensorManager.collectIsActiveState().value)

        // 1 -> unregister onInactive call
        verify(exactly = 1) { sensorManager.unregisterListener(any(), any<Sensor>()) }
    }

    @Test
    fun `when second subscriber stops collecting, then should not unregister listener`() {
        setupAndroidSmartSensorManager()
        collectSamples()
        val secondCollectingJob = collectSamples()
        // 1 -> Class instance initialization
        verify(exactly = 1) { context.getSystemService(Context.SENSOR_SERVICE) }

        assertEquals(1, inactiveCalls)
        assertTrue(androidSmartSensorManager.collectIsActiveState().value)
        secondCollectingJob.cancel()
        assertEquals(1, inactiveCalls)
        assertTrue(androidSmartSensorManager.collectIsActiveState().value)

        verify(exactly = 0) { sensorManager.unregisterListener(any(), any<Sensor>()) }
    }

    @Test
    fun `should not replay initialization failure event to second observer`() = runBlockingTest {
        setupContextMock(sensorManagerReturn = null)
        setupAndroidSmartSensorManager()

        androidSmartSensorManager.collectRawSensorSamples().test {
            val item = awaitItem()
            assertTrue(item is SensorData.Error)
            assertTrue((item as SensorData.Error).errorType is SensorData.ErrorType.InitializationFailure)
        }

        setupContextMock(sensorManagerReturn = sensorManager)

        androidSmartSensorManager.collectRawSensorSamples().test {
            expectNoEvents()
        }
    }

    private fun collectSamples() =
        testCoroutineScope.launch {
            androidSmartSensorManager.collectRawSensorSamples().collect {}
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
        setupListenerRegistrationAnswer(true)
        every { sensorManager.unregisterListener(any(), any<Sensor>()) } returns Unit
    }

    private fun setupContextMock(sensorManagerReturn: SensorManager?) {
        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManagerReturn
    }

    private fun setupGetDefaultSensorAnswer(sensor: Sensor?) {
        every { sensorManager.getDefaultSensor(any()) } returns sensor
    }

    private fun setupListenerRegistrationAnswer(registered: Boolean) {
        every { sensorManager.registerListener(any(), any<Sensor>(), any()) } returns registered
    }

    private var activeCalls = 0
    private var inactiveCalls = 0

    private fun setupAndroidSmartSensorManager() {
        activeCalls = 0
        inactiveCalls = 0

        androidSmartSensorManager =
            object : AndroidSmartSensorManager(context, 1, testCoroutineScope) {
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