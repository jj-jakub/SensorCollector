package com.jj.core.domain.travel.model

import java.util.UUID

data class TravelItem(
    val id: UUID? = UUID.randomUUID(),
    val text: String,
    val isChecked: Boolean
)
