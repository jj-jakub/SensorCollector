package com.jj.server.framework.managers

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.jj.server.domain.managers.VibrationManager

class AndroidVibrationManager(
    private val context: Context
) : VibrationManager {

    override fun vibrate(durationMillis: Long) {
         Log.d("ABABS", "AndroidVibrationManager vibrate for $durationMillis")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator?.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(durationMillis)
        }
    }
}