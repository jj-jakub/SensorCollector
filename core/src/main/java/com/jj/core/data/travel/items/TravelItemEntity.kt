package com.jj.core.data.travel.items

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TravelItemEntity(
    @PrimaryKey
    val id: Int? = null,
    val text: String,
    val isChecked: Boolean,
)