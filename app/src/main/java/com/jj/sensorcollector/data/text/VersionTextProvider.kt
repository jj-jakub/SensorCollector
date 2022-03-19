package com.jj.sensorcollector.data.text

import com.jj.sensorcollector.BuildConfig

class VersionTextProvider {

    fun getAboutVersionText(): String = "Revision: ${BuildConfig.currentRevisionHash}, " +
            "Build number: ${BuildConfig.ciBuildNumber}, Version: ${BuildConfig.VERSION_NAME}"
}