package com.jj.sensorcollector.playground1.framework.service

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LifecycleService
import com.jj.sensorcollector.framework.notification.NotificationManagerBuilder
import com.jj.sensorcollector.framework.services.NOTIFICATION_SERVICE_ID
import com.jj.sensorcollector.playground1.data.AccelerometerSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.samples.gps.GPSSampleAnalyzer
import org.koin.android.ext.android.inject

class SensorCollectorService : LifecycleService() {

    private val accelerometerSampleAnalyzer: AccelerometerSampleAnalyzer by inject()
    private val gpsSampleAnalyzer: GPSSampleAnalyzer by inject()
    private val notificationManagerBuilder: NotificationManagerBuilder by inject()

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_SERVICE_ID, notificationManagerBuilder.getServiceNotification(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ServiceAction.START_COLLECTING_ACCELEROMETER.action -> onStartCollectingAccelerometer()
            ServiceAction.STOP_COLLECTING_ACCELEROMETER.action -> onStopCollectingAccelerometer()
            ServiceAction.START_COLLECTING_GPS.action -> onStartCollectingGPS()
        }
        return START_STICKY
    }

    private fun onStartCollectingAccelerometer() {
        accelerometerSampleAnalyzer.startAnalysis()
    }

    private fun onStopCollectingAccelerometer() {
        accelerometerSampleAnalyzer.stopAnalysis()
    }

    private fun onStartCollectingGPS() {
        gpsSampleAnalyzer.startAnalysis()
    }

    companion object {
        private const val TAG = "SensorCollectorService"

        sealed class ServiceAction(val action: String) {
            object START_COLLECTING_ACCELEROMETER : ServiceAction("START_COLLECTING_ACCELEROMETER")
            object STOP_COLLECTING_ACCELEROMETER : ServiceAction("STOP_COLLECTING_ACCELEROMETER")
            object START_COLLECTING_GPS : ServiceAction("START_COLLECTING_GPS")
        }

        fun startCollectingAccelerometer(context: Context) {
            start(context, ServiceAction.START_COLLECTING_ACCELEROMETER)
        }

        fun stopCollectingAccelerometer(context: Context) {
            start(context, ServiceAction.STOP_COLLECTING_ACCELEROMETER)
        }

        fun startCollectingGPS(context: Context) {
            start(context, ServiceAction.START_COLLECTING_GPS)
        }

        private fun start(context: Context, serviceAction: ServiceAction) {
            val intent = Intent(context, SensorCollectorService::class.java).apply {
                action = serviceAction.action
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }
}