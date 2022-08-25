package com.jj.domain.hardware.accelerometer.usecase

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.general.AnalysisStarter

class StartAccelerometerAnalysis(
    private val analysisStarter: AnalysisStarter
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        analysisStarter.startPermanentAccelerometerAnalysis()
    }
}