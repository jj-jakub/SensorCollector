package com.jj.sensorcollector.playground1.framework.service

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LifecycleService
import com.jj.sensorcollector.framework.notification.NotificationManagerBuilder
import com.jj.sensorcollector.framework.services.NOTIFICATION_SERVICE_ID
import com.jj.sensorcollector.playground1.data.SampleAnalyzer
import org.koin.android.ext.android.inject

class SensorCollectorService : LifecycleService() {

    private val sampleAnalyzer: SampleAnalyzer by inject()
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
        }
        return START_STICKY
    }

    private fun onStartCollectingAccelerometer() {
        sampleAnalyzer.startAnalysis()
    }

    private fun onStopCollectingAccelerometer() {
        sampleAnalyzer.stopAnalysis()
    }

    companion object {
        private const val TAG = "SensorCollectorService"

        sealed class ServiceAction(val action: String) {
            object START_COLLECTING_ACCELEROMETER : ServiceAction("START_COLLECTING_ACCELEROMETER")
            object STOP_COLLECTING_ACCELEROMETER : ServiceAction("STOP_COLLECTING_ACCELEROMETER")
        }

        fun startCollectingAccelerometer(context: Context) {
            start(context, ServiceAction.START_COLLECTING_ACCELEROMETER)
        }

        fun stopCollectingAccelerometer(context: Context) {
            start(context, ServiceAction.STOP_COLLECTING_ACCELEROMETER)
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