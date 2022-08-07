package com.jj.core.framework.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.domain.travel.GetTravelItems
import com.jj.core.framework.domain.model.ChecklistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TravelScreenViewModel(
    private val getTravelItems: GetTravelItems
) : ViewModel() {

    private val _firstListItems = MutableStateFlow<List<ChecklistItem>>(listOf())
    val firstListItems = _firstListItems.asStateFlow()

    private val _secondListItems = MutableStateFlow<List<ChecklistItem>>(listOf())
    val secondListItems = _secondListItems.asStateFlow()

    init {
        viewModelScope.launch {
            _firstListItems.value = getTravelItems()
            _secondListItems.value = getTravelItems()
        }
    }
    fun onFirstListItemCheckedChange(checklistItem: ChecklistItem) {
        _firstListItems.value = firstListItems.value.map { item ->
            if (item == checklistItem) item.copy(isChecked = !item.isChecked) else item
        }
    }

    fun onSecondListItemCheckedChange(checklistItem: ChecklistItem) {
        _secondListItems.value = secondListItems.value.map { item ->
            if (item == checklistItem) item.copy(isChecked = !item.isChecked) else item
        }
    }
}