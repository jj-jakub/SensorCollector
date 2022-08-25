package com.jj.domain.hardware.gps.repository

import com.jj.domain.hardware.gps.model.PathData

interface PathRepository {

    fun insertData(pathData: PathData)
}