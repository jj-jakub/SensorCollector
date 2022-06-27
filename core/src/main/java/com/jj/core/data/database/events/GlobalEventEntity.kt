package com.jj.core.data.database.events

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GlobalEventEntity(
        @PrimaryKey
        val id: Int? = null,
        val time: Long,
        val type: String
)
