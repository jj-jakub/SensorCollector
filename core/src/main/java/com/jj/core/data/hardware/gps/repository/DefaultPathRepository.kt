package com.jj.core.data.hardware.gps.repository

import com.jj.core.data.database.gps.analysis.path.GPSPathDataDao
import com.jj.core.data.database.gps.analysis.path.toGPSPathData
import com.jj.core.data.database.gps.analysis.path.toGPSPathDataEntity
import com.jj.domain.hardware.gps.model.GPSPathData
import com.jj.domain.hardware.gps.repository.PathRepository
import kotlinx.coroutines.flow.map

class DefaultPathRepository(
    private val gpsPathDataDao: GPSPathDataDao
) : PathRepository {

    override suspend fun insertData(gpsPathData: GPSPathData) =
        gpsPathDataDao.insert(gpsPathData.toGPSPathDataEntity()).toInt()

    override suspend fun getPathData(pathId: Int) = gpsPathDataDao.getGPSPathDataEntityForPathId(pathId = pathId)?.toGPSPathData()

    override fun getAllPathData() =
        gpsPathDataDao.getGPSPathDataEntities().map { entities -> entities.map { entity -> entity.toGPSPathData() } }

    override fun getLatestPathData() = gpsPathDataDao.getLatestGPSPathDataEntity().map { it.toGPSPathData() }
}