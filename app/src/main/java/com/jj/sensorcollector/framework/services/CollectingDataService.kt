package com.jj.sensorcollector.framework.services

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager
import com.jj.sensorcollector.framework.notification.NotificationManagerBuilder
import com.jj.sensorcollector.framework.services.CollectingDataService.Companion.ServiceAction.START_COLLECTING_ACCELEROMETER
import com.jj.sensorcollector.framework.services.CollectingDataService.Companion.ServiceAction.START_COLLECTING_GPS
import com.jj.sensorcollector.framework.services.CollectingDataService.Companion.ServiceAction.STOP_COLLECTING_ACCELEROMETER
import com.jj.sensorcollector.framework.services.CollectingDataService.Companion.ServiceAction.STOP_COLLECTING_GPS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

const val NOTIFICATION_MAIN_ID = 1
const val NOTIFICATION_MAIN_CHANNEL_ID = "NOTIFICATION_MAIN_CHANNEL"
const val NOTIFICATION_MAIN_CHANNEL_NAME = "Main notification"

const val NOTIFICATION_SERVICE_ID = 2
const val NOTIFICATION_SERVICE_CHANNEL_ID = "NOTIFICATION_SERVICE_CHANNEL"
const val NOTIFICATION_SERVICE_CHANNEL_NAME = "Service notification"

class CollectingDataService : LifecycleService() {

    private val globalSensorManager: IGlobalSensorManager by inject()
    private val notificationManagerBuilder: NotificationManagerBuilder by inject()

    private val isWorking = MutableStateFlow(false)

    inner class LocalBinder(val service: CollectingDataService) : Binder()

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        Log.d(TAG, "onBind")
        return LocalBinder(this)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        startForeground(NOTIFICATION_SERVICE_ID, notificationManagerBuilder.getServiceNotification(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        when (intent?.action) {
            START_COLLECTING_ACCELEROMETER.action -> onStartCollectingAccelerometer()
            START_COLLECTING_GPS.action -> onStartCollectingGPS()

            STOP_COLLECTING_ACCELEROMETER.action -> onStopCollectingAccelerometer()
            STOP_COLLECTING_GPS.action -> onStopCollectingGPS()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun onStartCollectingAccelerometer() {
        globalSensorManager.startAccelerometer()
    }

    private fun onStartCollectingGPS() {
        globalSensorManager.startGPS()
    }

    private fun onStopCollectingAccelerometer() {
        globalSensorManager.stopAccelerometer()
    }

    private fun onStopCollectingGPS() {
        globalSensorManager.stopGPS()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        notificationManagerBuilder.cancelServiceNotification(this)
//        sensorController?.stopReceivingData()
        isWorking.value = false
        super.onDestroy()
    }

    companion object : ServiceStarter {
        private const val TAG = "CollectingDataService"

        sealed class ServiceAction(val action: String) {
            object START_COLLECTING_ACCELEROMETER : ServiceAction("START_COLLECTING_ACCELEROMETER")
            object START_COLLECTING_GPS : ServiceAction("START_COLLECTING_GPS")
            object STOP_COLLECTING_ACCELEROMETER : ServiceAction("STOP_COLLECTING_ACCELEROMETER")
            object STOP_COLLECTING_GPS : ServiceAction("STOP_COLLECTING_GPS")
        }

        override fun getServiceClass() = CollectingDataService::class.java

        fun startCollectingAccelerometer(context: Context) {
            startWithAction(context, START_COLLECTING_ACCELEROMETER.action)
        }

        fun startCollectingGPS(context: Context) {
            startWithAction(context, START_COLLECTING_GPS.action)
        }

        @Suppress("SameParameterValue")
        private fun startWithAction(context: Context, intentAction: String) {
            val intent = getServiceIntent(context).apply { action = intentAction }
            start(context, intent)
        }

        fun stopCollectingAccelerometer(context: Context) = start(context, STOP_COLLECTING_ACCELEROMETER.action)
        fun stopCollectingGPS(context: Context) = start(context, STOP_COLLECTING_GPS.action)
    }
}