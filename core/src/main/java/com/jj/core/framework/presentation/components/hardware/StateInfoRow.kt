package com.jj.core.framework.presentation.components.hardware

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jj.core.framework.text.AndroidColorMapper.toTextColor
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.monitoring.model.toTextAndColor

@Composable
fun StateInfoRow(firstLabel: String, state: SystemModuleState) {
    val textAndColor = state.toTextAndColor()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = firstLabel
        )
        Text(
            text = textAndColor.first,
            color = Color(textAndColor.second.toTextColor())
        )
    }
}