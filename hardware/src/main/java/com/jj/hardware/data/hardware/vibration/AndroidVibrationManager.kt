package com.jj.hardware.data.hardware.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.jj.domain.hardware.vibration.managers.VibrationManager

class AndroidVibrationManager(
    private val context: Context
) : VibrationManager {

    override fun vibrate(durationMillis: Long): Boolean = try {
        Log.d("ABABS", "AndroidVibrationManager vibrate for $durationMillis")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator?.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(durationMillis)
        }
        true
    } catch (e: Exception) {
        false
    }
}