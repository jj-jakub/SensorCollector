package com.jj.sensorcollector.playground1.data

import com.jj.sensorcollector.playground1.domain.managers.SampleXAnalyzer
import com.jj.sensorcollector.playground1.domain.managers.ScreenStateCollector
import com.jj.sensorcollector.playground1.domain.managers.SoundManager

class Initializator(
    private val sampleXAnalyzer: SampleXAnalyzer,
    private val screenStateCollector: ScreenStateCollector,
    private val soundManager: SoundManager
) {

}