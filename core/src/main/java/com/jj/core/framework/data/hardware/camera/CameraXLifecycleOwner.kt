package com.jj.core.framework.data.hardware.camera

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

class CameraXLifecycleOwner : LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return object : Lifecycle() {
            override fun addObserver(observer: LifecycleObserver) {
                /* no-op */
            }

            override fun removeObserver(observer: LifecycleObserver) {
                /* no-op */
            }

            override fun getCurrentState(): State {
                return State.RESUMED
            }
        }
    }
}
