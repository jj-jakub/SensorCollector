package com.jj.sensorcollector.playground1.data.managers

import android.util.Log
import com.jj.sensors.domain.managers.SoundManager

class DefaultSoundManager: SoundManager {

    init {
        Log.d("ABAB", "DefaultSoundManager created")
    }

    override fun play(duration: Long) {
        Log.d("ABAB", "Playing sound for $duration")
    }
}