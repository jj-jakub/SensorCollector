package com.jj.sensorcollector.playground1.data.accanalyzers

import com.jj.sensorcollector.playground1.domain.AccSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.SensorData

class AccAnalyzer3: AccSampleAnalyzer {

    override fun analyze(sensorData: SensorData.AccSample): Int {
        return 10
    }
}