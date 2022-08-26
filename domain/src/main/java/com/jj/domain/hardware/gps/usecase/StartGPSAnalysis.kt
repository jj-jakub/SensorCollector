package com.jj.domain.hardware.gps.usecase

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.gps.analysis.GPSSampleAnalyser

class StartGPSAnalysis(
    private val gpsSampleAnalyser: GPSSampleAnalyser
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        gpsSampleAnalyser.startAnalysis()
    }
}