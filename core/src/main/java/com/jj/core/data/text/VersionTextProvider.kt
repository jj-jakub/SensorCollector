package com.jj.core.data.text

import com.jj.core.BuildConfig

class VersionTextProvider {

    fun getAboutVersionText(): String = "Revision: ${BuildConfig.currentRevisionHash}, " +
            "Build number: ${BuildConfig.ciBuildNumber}, Version: -"// ${BuildConfig.VERSION_NAME}"
}