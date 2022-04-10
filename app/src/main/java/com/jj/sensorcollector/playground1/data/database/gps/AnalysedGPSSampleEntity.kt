package com.jj.sensorcollector.playground1.data.database.gps

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