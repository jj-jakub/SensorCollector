package com.jj.core.data.database.gps.analysis

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnalysedGPSSampleEntity(
    @PrimaryKey
    val id: Int? = null,
    val latitude: Double,
    val longitude: Double,
    val sampleTime: Long
)