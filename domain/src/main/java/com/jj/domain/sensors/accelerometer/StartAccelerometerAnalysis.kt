package com.jj.domain.sensors.accelerometer

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.sensors.general.AnalysisStarter

class StartAccelerometerAnalysis(
    private val analysisStarter: AnalysisStarter
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        analysisStarter.startPermanentAccelerometerAnalysis()
    }
}