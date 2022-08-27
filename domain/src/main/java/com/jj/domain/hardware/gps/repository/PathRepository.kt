package com.jj.domain.hardware.gps.repository

import com.jj.domain.hardware.gps.model.GPSPathData
import kotlinx.coroutines.flow.Flow

interface PathRepository {
    suspend fun insertData(gpsPathData: GPSPathData): Int
    suspend fun getPathData(pathId: Int): GPSPathData?
    fun getLatestPathData(): Flow<GPSPathData>
    fun getAllPathData(): Flow<List<GPSPathData>>
}