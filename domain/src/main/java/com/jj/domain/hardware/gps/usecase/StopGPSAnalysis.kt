package com.jj.domain.hardware.gps.usecase

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.gps.analysis.GPSSampleAnalyser

class StopGPSAnalysis(
    private val gpsSampleAnalyser: GPSSampleAnalyser
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        gpsSampleAnalyser.stopAnalysis()
    }
}