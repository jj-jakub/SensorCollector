package com.jj.core.data.database.samples.accelerometer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccelerationDataEntity(
        @PrimaryKey
        val id: Int? = null,
        val x: Float,
        val y: Float,
        val z: Float,
        val time: Long
)
