package com.jj.core.framework.presentation.travel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jj.core.domain.travel.model.TravelItem
import org.koin.androidx.compose.getViewModel

@Composable
fun TravelScreen(
    viewModel: TravelScreenViewModel = getViewModel()
) {
    val firstChecklist by viewModel.firstListItems.collectAsState()
    val secondChecklist by viewModel.secondListItems.collectAsState()
    val addTravelItemText by viewModel.addTravelItemText.collectAsState()

    TravelScreenContent(
        firstTravelItems = firstChecklist,
        secondTravelItems = secondChecklist,
        onFirstTravelItemsCheckedChange = viewModel::onFirstListItemCheckedChange,
        onSecondTravelItemsCheckedChange = viewModel::onSecondListItemCheckedChange,
        addTravelItemText = addTravelItemText,
        onAddTravelItemTextChanged = viewModel::onAddTravelItemTextChanged,
        onAddTravelItemSaveClicked = viewModel::onAddTravelItemSaveClicked,
        onDeleteTravelItem = viewModel::onDeleteTravelItem,
    )
}

@Composable
private fun TravelScreenContent(
    firstTravelItems: List<TravelItem>,
    secondTravelItems: List<TravelItem>,
    onFirstTravelItemsCheckedChange: (TravelItem) -> Unit,
    onSecondTravelItemsCheckedChange: (TravelItem) -> Unit,
    addTravelItemText: String,
    onAddTravelItemTextChanged: (String) -> Unit,
    onAddTravelItemSaveClicked: () -> Unit,
    onDeleteTravelItem: (TravelItem) -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        AddTravelItemSection(
            addTravelItemText = addTravelItemText,
            onAddTravelItemTextChanged = onAddTravelItemTextChanged,
            onAddTravelItemSaveClicked = onAddTravelItemSaveClicked
        )
        ItemsSection(
            modifier = Modifier.weight(1f),
            firstTravelItems = firstTravelItems,
            secondTravelItems = secondTravelItems,
            onFirstTravelItemsCheckedChange = onFirstTravelItemsCheckedChange,
            onSecondTravelItemsCheckedChange = onSecondTravelItemsCheckedChange,
            onDeleteTravelItem = onDeleteTravelItem,
        )
    }
}

@Composable
private fun ItemsSection(
    modifier: Modifier,
    firstTravelItems: List<TravelItem>,
    secondTravelItems: List<TravelItem>,
    onFirstTravelItemsCheckedChange: (TravelItem) -> Unit,
    onSecondTravelItemsCheckedChange: (TravelItem) -> Unit,
    onDeleteTravelItem: (TravelItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ItemsList(
                listTitle = "Ja",
                listItems = firstTravelItems,
                onCheckedChange = onFirstTravelItemsCheckedChange,
                onDeleteTravelItem = onDeleteTravelItem,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ItemsList(
                listTitle = "Misiun",
                listItems = secondTravelItems,
                onCheckedChange = onSecondTravelItemsCheckedChange,
                onDeleteTravelItem = onDeleteTravelItem,
            )
        }
    }
}

@Composable
private fun AddTravelItemSection(
    addTravelItemText: String,
    onAddTravelItemTextChanged: (String) -> Unit,
    onAddTravelItemSaveClicked: () -> Unit
) {
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = addTravelItemText,
            onValueChange = onAddTravelItemTextChanged,
            label = {
                Text(text = "Write new item")
            }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(text = "Save new item")
            },
            onClick = onAddTravelItemSaveClicked,
        )
    }
}

@Composable
private fun ItemsList(
    listTitle: String,
    listItems: List<TravelItem>,
    onCheckedChange: (TravelItem) -> Unit,
    onDeleteTravelItem: (TravelItem) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(listTitle, textAlign = TextAlign.Center)
        listItems.forEach {
            ListItem(
                travelItem = it,
                onCheckedChange = onCheckedChange,
                onDeleteTravelItem = onDeleteTravelItem,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListItem(
    travelItem: TravelItem,
    onCheckedChange: (TravelItem) -> Unit,
    onDeleteTravelItem: (TravelItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = { onDeleteTravelItem(travelItem) },
                onClick = {}
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = travelItem.text)
        Checkbox(checked = travelItem.isChecked, onCheckedChange = { onCheckedChange(travelItem) })
    }
}

@Preview
@Composable
fun PreviewTravelScreen() {
    TravelScreenContent(
        firstTravelItems = getTestListItems(),
        secondTravelItems = getTestListItems(),
        onFirstTravelItemsCheckedChange = {},
        onSecondTravelItemsCheckedChange = {},
        addTravelItemText = "addTravelItemText",
        onAddTravelItemTextChanged = {},
        onAddTravelItemSaveClicked = {},
        onDeleteTravelItem = {},
    )
}

private fun getTestListItems() = mutableListOf<TravelItem>().apply {
    repeat(5) {
        add(
            TravelItem(
                text = "A",
                isChecked = false,
                listId = ""
            )
        )
    }
}