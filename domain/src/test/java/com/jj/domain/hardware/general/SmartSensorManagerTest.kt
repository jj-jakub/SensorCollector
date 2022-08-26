package com.jj.domain.hardware.general

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SmartSensorManagerTest {

    private lateinit var smartSensorManager: SmartSensorManager<Unit>

    private lateinit var testCoroutineScope: CoroutineScope

    @BeforeEach
    fun setup() {
        testCoroutineScope = TestScope()
        smartSensorManager = object : SmartSensorManager<Unit>() {
            init {
                testCoroutineScope.launch {
                    start()
                }
            }

            override suspend fun onActive(): Boolean = true
        }
    }

    @Test
    fun `isActiveState should be false after class init`() {
        assertFalse(smartSensorManager.collectSensorActivityState().value)
    }

    @Test
    fun `isActiveState should be true if there is active observing job`() = runTest {
        launch { smartSensorManager.collectSensorActivityState().collect {} }
        assertTrue(smartSensorManager.collectSensorActivityState().value)
    }
}