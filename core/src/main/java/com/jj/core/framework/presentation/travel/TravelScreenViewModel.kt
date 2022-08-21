package com.jj.core.framework.presentation.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.domain.travel.DeleteTravelItem
import com.jj.core.domain.travel.GetTravelItemsForList
import com.jj.core.domain.travel.SaveTravelItem
import com.jj.core.domain.travel.model.TravelItem
import com.jj.core.domain.travel.model.TravelList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TravelScreenViewModel(
    private val getTravelItemsForList: GetTravelItemsForList,
    private val saveTravelItem: SaveTravelItem,
    private val deleteTravelItem: DeleteTravelItem,
) : ViewModel() {

    private val _firstListItems = MutableStateFlow(listOf<TravelItem>())
    val firstListItems = _firstListItems.asStateFlow()

    private val _secondListItems = MutableStateFlow(listOf<TravelItem>())
    val secondListItems = _secondListItems.asStateFlow()

    private val _addTravelItemText = MutableStateFlow("")
    val addTravelItemText = _addTravelItemText.asStateFlow()

    init {
        viewModelScope.launch {
            getTravelItemsForList(TravelList.MY.listId).collectLatest {
                _firstListItems.value = it.asReversed()
            }
        }
        viewModelScope.launch {
            getTravelItemsForList(TravelList.HER.listId).collectLatest {
                _secondListItems.value = it.asReversed()
            }
        }
    }

    fun onFirstListItemCheckedChange(travelItem: TravelItem) {
        saveItem(travelItem = travelItem.copy(isChecked = !travelItem.isChecked))
    }

    fun onSecondListItemCheckedChange(travelItem: TravelItem) {
        saveItem(travelItem = travelItem.copy(isChecked = !travelItem.isChecked))
    }

    private fun saveItem(travelItem: TravelItem) {
        viewModelScope.launch {
            saveTravelItem.invoke(travelItem)
        }
    }

    fun onAddTravelItemTextChanged(newText: String) {
        _addTravelItemText.value = newText
    }

    fun onAddTravelItemSaveClicked() {
        val textToSave = addTravelItemText.value.filter { it != '\n' }
        if (textToSave.isNotBlank()) {
            saveItem(TravelItem(listId = TravelList.MY.listId, text = textToSave, isChecked = false))
            saveItem(TravelItem(listId = TravelList.HER.listId, text = textToSave, isChecked = false))
            _addTravelItemText.value = ""
        }
    }

    fun onDeleteTravelItem(travelItem: TravelItem) {
        viewModelScope.launch {
            deleteTravelItem(travelItem = travelItem)
        }
    }
}