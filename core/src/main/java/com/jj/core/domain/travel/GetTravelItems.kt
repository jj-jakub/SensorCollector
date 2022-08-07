package com.jj.core.domain.travel

import com.jj.core.framework.domain.model.Checklist
import com.jj.core.framework.domain.model.ChecklistId
import com.jj.core.framework.domain.model.ChecklistItem

class GetTravelItems {

    suspend operator fun invoke(checklistId: ChecklistId): Checklist {
        // TODO
        return Checklist(
            checklistId.name, listOf(
                ChecklistItem(
                    name = "A",
                    isChecked = false
                ),
                ChecklistItem(
                    name = "B",
                    isChecked = true
                ),
            )
        )
    }
}