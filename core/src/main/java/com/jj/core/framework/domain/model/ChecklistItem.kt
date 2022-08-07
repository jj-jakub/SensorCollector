package com.jj.core.framework.domain.model

import java.util.UUID

data class ChecklistItem(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val isChecked: Boolean
)
