package com.jj.core.domain.repository

import com.jj.core.domain.samples.samples.gps.PathData

interface PathRepository {

    fun insertData(pathData: PathData)
}