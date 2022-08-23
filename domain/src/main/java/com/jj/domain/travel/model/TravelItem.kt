package com.jj.domain.travel.model

data class TravelItem(
    val id: Int? = null,
    val listId: String,
    val text: String,
    val isChecked: Boolean
)
