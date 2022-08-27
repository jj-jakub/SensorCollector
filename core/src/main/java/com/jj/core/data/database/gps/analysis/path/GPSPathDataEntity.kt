package com.jj.core.data.database.gps.analysis.path

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GPSPathDataEntity(
    @PrimaryKey
    val id: Int? = null,
    val startDate: Long,
    val endDate: Long? = null,
    val startLatitude: Double? = null,
    val endLatitude: Double? = null,
    val startLongitude: Double? = null,
    val endLongitude: Double? = null,
)