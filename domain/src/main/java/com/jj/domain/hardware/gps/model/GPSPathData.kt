package com.jj.domain.hardware.gps.model

import java.util.Date

data class GPSPathData(
    val id: Int?,
    val startDate: Date,
    val endDate: Date?,
    val startLatitude: Double?,
    val endLatitude: Double?,
    val startLongitude: Double?,
    val endLongitude: Double?,
)