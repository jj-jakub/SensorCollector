package com.jj.sensorcollector.data.database.samples

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GPSDataEntity(
        @PrimaryKey
        val id: Int? = null,
        val time: Long,
        val lat: Double,
        val lng: Double,
)
