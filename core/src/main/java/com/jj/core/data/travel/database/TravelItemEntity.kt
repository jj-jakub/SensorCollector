package com.jj.core.data.travel.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TravelItemEntity(
    @PrimaryKey
    val id: Int? = null,
    val listId: String,
    val text: String,
    val isChecked: Boolean,
)