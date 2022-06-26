package com.jj.sensorcollector.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import com.jj.sensorcollector.playground1.framework.presentation.charts.AnalysedAccelerometerThreeAxisLinearChart
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor

@Composable
fun MainScreen(
    sensorsDataViewModel: SensorsDataViewModel
) {
    val state by sensorsDataViewModel.accelerometerCollectionState.collectAsState()

    MainScreenContent(state)
}

@Composable
private fun MainScreenContent(accelerometerStateView: SystemModuleState) {
    Column {
        AccelerometerChart()
        AccelerometerChart()
        AccelerometerChart()

        Divider(thickness = 10.dp)

        AccelerometerChart()
        AccelerometerChart()
        AccelerometerChart()

        AccelerometerStateView(accelerometerStateView)
    }
}

@Composable
fun AccelerometerStateView(state: SystemModuleState) {
    val textAndColor = getTextForSystemModuleState(state)
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Accelerometer state: ")
        Text(
            text = "Acc state val: ${textAndColor.first}",
            color = Color(textAndColor.second.toTextColor())
        )
    }
}

@Composable
private fun AccelerometerChart() {
    AndroidView(
        modifier = Modifier
            .height(45.dp)
            .width(100.dp),
        factory = { context ->
            AnalysedAccelerometerThreeAxisLinearChart(context = context)
        }
    )
}

private fun getTextForSystemModuleState(systemModuleState: SystemModuleState): Pair<String, DomainColor> {
    return when (systemModuleState) {
        is SystemModuleState.Off -> {
            if (systemModuleState == SystemModuleState.Off.OnButTimeExceeded) {
                "TimeExceeded" to DomainColor.Orange
            } else {
                "Off" to DomainColor.Red
            }
        }
        SystemModuleState.Starting -> "Starting" to DomainColor.Yellow
        SystemModuleState.Unknown -> "Unknown" to DomainColor.Yellow
        SystemModuleState.Working -> "Working" to DomainColor.Green
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreenContent(
        accelerometerStateView = SystemModuleState.Unknown
    )
}