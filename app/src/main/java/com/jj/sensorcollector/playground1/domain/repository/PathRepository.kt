package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.samples.gps.PathData

interface PathRepository {

    fun insertData(pathData: PathData)
}