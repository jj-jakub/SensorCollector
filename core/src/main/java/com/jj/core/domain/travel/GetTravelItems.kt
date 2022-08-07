package com.jj.core.domain.travel

import com.jj.core.framework.domain.model.ChecklistItem

class GetTravelItems {

    suspend operator fun invoke(): List<ChecklistItem> {
        // TODO
        return listOf(
            ChecklistItem(
                name = "A",
                isChecked = false
            ),
            ChecklistItem(
                name = "B",
                isChecked = true
            ),
        )
    }
}