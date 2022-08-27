package com.jj.domain.hardware.gps.usecase.path

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.gps.repository.PathRepository
import com.jj.domain.time.TimeProvider
import java.util.Date

class FinishPath(
    private val pathRepository: PathRepository,
    private val timeProvider: TimeProvider,
) : UseCase<Int, Unit> {

    override suspend fun invoke(pathId: Int) {
        val pathData = pathRepository.getPathData(pathId = pathId)
        if (pathData != null) {
            val updatedPathData = pathData.copy(endDate = Date(timeProvider.getNowMillis()))
            pathRepository.insertData(updatedPathData)
        } else {
            // TODO Return error
        }
    }
}