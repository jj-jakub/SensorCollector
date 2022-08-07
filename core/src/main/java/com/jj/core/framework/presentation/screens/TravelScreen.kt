package com.jj.core.framework.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jj.core.framework.domain.model.ChecklistItem
import com.jj.core.framework.presentation.viewmodels.TravelScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun TravelScreen(
    viewModel: TravelScreenViewModel = getViewModel()
) {

    val firstListItems by viewModel.firstListItems.collectAsState()
    val secondListItems by viewModel.secondListItems.collectAsState()

    TravelScreenContent(
        firstListItems = firstListItems,
        secondListItems = secondListItems,
        onFirstListItemCheckedChange = viewModel::onFirstListItemCheckedChange,
        onSecondListItemCheckedChange = viewModel::onSecondListItemCheckedChange
    )
}

@Composable
private fun TravelScreenContent(
    firstListItems: List<ChecklistItem>,
    secondListItems: List<ChecklistItem>,
    onFirstListItemCheckedChange: (ChecklistItem) -> Unit,
    onSecondListItemCheckedChange: (ChecklistItem) -> Unit,
) {
    Row {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ItemsList(
                listTitle = "Ja",
                listItems = firstListItems,
                onCheckedChange = onFirstListItemCheckedChange
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ItemsList(
                listTitle = "Siwy",
                listItems = secondListItems,
                onCheckedChange = onSecondListItemCheckedChange
            )
        }
    }
}

@Composable
private fun ItemsList(listTitle: String, listItems: List<ChecklistItem>, onCheckedChange: (ChecklistItem) -> Unit) {
    Column {
        Text(listTitle, textAlign = TextAlign.Center)
        listItems.forEach {
            ListItem(
                checklistItem = it,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun ListItem(checklistItem: ChecklistItem, onCheckedChange: (ChecklistItem) -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = checklistItem.name)
        Checkbox(checked = checklistItem.isChecked, onCheckedChange = { onCheckedChange(checklistItem) })
    }
}

@Preview
@Composable
fun PreviewTravelScreen() {
    TravelScreenContent(
        firstListItems = listOf(
            ChecklistItem(
                name = "A",
                isChecked = false
            ),
            ChecklistItem(
                name = "B",
                isChecked = true
            ),
        ),
        secondListItems = listOf(
            ChecklistItem(
                name = "C",
                isChecked = true
            ),
            ChecklistItem(
                name = "D",
                isChecked = false
            ),
        ),
        onFirstListItemCheckedChange = {},
        onSecondListItemCheckedChange = {}
    )
}