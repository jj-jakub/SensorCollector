package com.jj.domain.hardware.general.usecase

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.general.AnalysisStarter

class StartAnalysis(
    private val analysisStarter: AnalysisStarter
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        analysisStarter.startPermanentAccelerometerAnalysis()
        analysisStarter.startPermanentGPSAnalysis()
    }
}