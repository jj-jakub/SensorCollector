package com.jj.core.framework.presentation.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.domain.travel.GetTravelItems
import com.jj.core.domain.travel.SaveTravelItems
import com.jj.core.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TravelScreenViewModel(
    private val getTravelItems: GetTravelItems,
    private val saveTravelItems: SaveTravelItems,
) : ViewModel() {

    private val _firstListItems = MutableStateFlow(listOf<TravelItem>())
    val firstListItems = _firstListItems.asStateFlow()

    private val _secondListItems = MutableStateFlow(listOf<TravelItem>())
    val secondListItems = _secondListItems.asStateFlow()

    init {
        viewModelScope.launch {
            getTravelItems().collectLatest {
                _firstListItems.value = it
                _secondListItems.value = it
            }
        }
    }

    fun onFirstListItemCheckedChange(travelItem: TravelItem) {
        saveItem(travelItem = travelItem)
    }

    fun onSecondListItemCheckedChange(travelItem: TravelItem) {
        saveItem(travelItem = travelItem)
    }

    private fun saveItem(travelItem: TravelItem) {
        viewModelScope.launch {
            saveTravelItems.invoke(travelItem)
        }
    }
}