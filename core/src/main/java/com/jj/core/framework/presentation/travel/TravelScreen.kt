package com.jj.core.framework.presentation.travel

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
import com.jj.core.domain.travel.model.TravelItem
import org.koin.androidx.compose.getViewModel

@Composable
fun TravelScreen(
    viewModel: TravelScreenViewModel = getViewModel()
) {
    val firstChecklist by viewModel.firstListItems.collectAsState()
    val secondChecklist by viewModel.secondListItems.collectAsState()

    TravelScreenContent(
        firstChecklist = firstChecklist,
        secondChecklist = secondChecklist,
        onFirstListItemCheckedChange = viewModel::onFirstListItemCheckedChange,
        onSecondListItemCheckedChange = viewModel::onSecondListItemCheckedChange
    )
}

@Composable
private fun TravelScreenContent(
    firstChecklist: List<TravelItem>,
    secondChecklist: List<TravelItem>,
    onFirstListItemCheckedChange: (TravelItem) -> Unit,
    onSecondListItemCheckedChange: (TravelItem) -> Unit,
) {
    Row {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ItemsList(
                listTitle = "1",
                listItems = firstChecklist,
                onCheckedChange = onFirstListItemCheckedChange
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ItemsList(
                listTitle = "2",
                listItems = secondChecklist,
                onCheckedChange = onSecondListItemCheckedChange
            )
        }
    }
}

@Composable
private fun ItemsList(listTitle: String, listItems: List<TravelItem>, onCheckedChange: (TravelItem) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(listTitle, textAlign = TextAlign.Center)
        listItems.forEach {
            ListItem(
                TravelItem = it,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun ListItem(TravelItem: TravelItem, onCheckedChange: (TravelItem) -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = TravelItem.text)
        Checkbox(checked = TravelItem.isChecked, onCheckedChange = { onCheckedChange(TravelItem) })
    }
}

@Preview
@Composable
fun PreviewTravelScreen() {
    TravelScreenContent(
        firstChecklist = listOf(
            TravelItem(
                text = "A",
                isChecked = false
            ),
            TravelItem(
                text = "B",
                isChecked = true
            ),
        ),
        secondChecklist = listOf(
            TravelItem(
                text = "C",
                isChecked = true
            ),
            TravelItem(
                text = "D",
                isChecked = false
            ),
        ),
        onFirstListItemCheckedChange = {},
        onSecondListItemCheckedChange = {}
    )
}