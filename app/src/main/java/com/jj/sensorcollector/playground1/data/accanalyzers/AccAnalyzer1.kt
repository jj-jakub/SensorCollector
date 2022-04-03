package com.jj.sensorcollector.playground1.data.accanalyzers

import com.jj.sensorcollector.playground1.domain.AccSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.SensorData

class AccAnalyzer1: AccSampleAnalyzer {

    override fun analyze(sensorData: SensorData.AccSample): Int {
        return 10
    }
}