package com.jj.core.framework.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.domain.travel.GetTravelItems
import com.jj.core.domain.travel.SaveTravelItems
import com.jj.core.framework.domain.model.Checklist
import com.jj.core.framework.domain.model.ChecklistId
import com.jj.core.framework.domain.model.ChecklistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TravelScreenViewModel(
    private val getTravelItems: GetTravelItems,
    private val saveTravelItems: SaveTravelItems,
) : ViewModel() {

    private val _firstListItems = MutableStateFlow(Checklist(id = "", items = listOf()))
    val firstListItems = _firstListItems.asStateFlow()

    private val _secondListItems = MutableStateFlow(Checklist(id = "", items = listOf()))
    val secondListItems = _secondListItems.asStateFlow()

    init {
        viewModelScope.launch {
            _firstListItems.value = getTravelItems(ChecklistId.MY)
            _secondListItems.value = getTravelItems(ChecklistId.HER)
        }
    }

    fun onFirstListItemCheckedChange(checklistItem: ChecklistItem) {
        _firstListItems.value = firstListItems.value.copy(items = firstListItems.value.items.map { item ->
            if (item == checklistItem) item.copy(isChecked = !item.isChecked) else item
        }
        )

        saveItems(_firstListItems.value)
    }

    fun onSecondListItemCheckedChange(checklistItem: ChecklistItem) {
        _secondListItems.value = secondListItems.value.copy(items = secondListItems.value.items.map { item ->
            if (item == checklistItem) item.copy(isChecked = !item.isChecked) else item
        }
        )

        saveItems(_secondListItems.value)
    }

    private fun saveItems(checklist: Checklist) {
        viewModelScope.launch {
            saveTravelItems.invoke(checklist)
        }
    }
}