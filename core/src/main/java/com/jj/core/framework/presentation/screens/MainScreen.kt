package com.jj.core.framework.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.core.domain.monitors.toTextAndColor
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.analysis.AnalysedSample
import com.jj.core.domain.samples.analysis.AnalysedValue
import com.jj.core.domain.samples.analysis.AnalysisResult
import com.jj.core.framework.domain.samples.AndroidAnalysedAccUIData
import com.jj.core.framework.presentation.SensorsDataViewModel
import com.jj.core.framework.presentation.charts.AnalysedAccelerometerThreeAxisLinearChart
import com.jj.core.framework.text.AndroidColorMapper.toTextColor
import com.jj.design.CameraPreview
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    sensorsDataViewModel: SensorsDataViewModel = getViewModel()
) {
    val accelerometerState by sensorsDataViewModel.accelerometerCollectionState.collectAsState()
    val accelerometerSample by sensorsDataViewModel.analysedAccelerometerSampleString.collectAsState(initial = null)
    val gyroscopeState by sensorsDataViewModel.gyroscopeCollectionState.collectAsState()
    val gyroscopeSample by sensorsDataViewModel.gyroscopeSamples.collectAsState(initial = null)
    val magneticFieldState by sensorsDataViewModel.magneticFieldCollectionState.collectAsState()
    val magneticFieldSample by sensorsDataViewModel.magneticFieldSamples.collectAsState(initial = null)
    val gpsState by sensorsDataViewModel.gpsCollectionState.collectAsState()
    val gpsSample by sensorsDataViewModel.gpsSamples.collectAsState(initial = null)

    MainScreenContent(
        sensorsDataViewModel::onStartAccelerometerClick,
        sensorsDataViewModel::onStopAccelerometerClick,
        sensorsDataViewModel.versionInfoText,
        sensorsDataViewModel.ipAddressText,
        accelerometerState = accelerometerState,
        accelerometerSample = accelerometerSample,
        gyroscopeState = gyroscopeState,
        gyroscopeSample = gyroscopeSample,
        magneticFieldState = magneticFieldState,
        magneticFieldSample = magneticFieldSample,
        gpsState = gpsState,
        gpsSample = gpsSample
    )
}

@Composable
private fun MainScreenContent(
    onStartAccelerometerClick: () -> Unit,
    onStopAccelerometerClick: () -> Unit,
    versionInfoText: String,
    ipAddressText: String,
    accelerometerState: SystemModuleState,
    accelerometerSample: AndroidAnalysedAccUIData?,
    gyroscopeState: SystemModuleState,
    gyroscopeSample: SensorData?,
    magneticFieldState: SystemModuleState,
    magneticFieldSample: SensorData?,
    gpsState: SystemModuleState,
    gpsSample: SensorData?
) {
    Column {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            AccelerometerChartsRow(accelerometerSample = accelerometerSample)

            Divider(thickness = 10.dp)

            AccelerometerChartsRow(accelerometerSample = accelerometerSample)

            AccelerometerStateView(state = accelerometerState)
            AccelerometerValueView(androidAnalysedAccUIData = accelerometerSample)
            AccelerometerControlButtons(
                onStartAccelerometerClick = onStartAccelerometerClick,
                onStopAccelerometerClick = onStopAccelerometerClick
            )

            GyroscopeStateView(state = gyroscopeState)
            GyroscopeValueView(sensorData = gyroscopeSample)

            MagneticFieldStateView(state = magneticFieldState)
            MagneticFieldValueView(sensorData = magneticFieldSample)

            GPSStateView(state = gpsState)
            GPSValueView(sensorData = gpsSample)

            CameraPreview(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
        }

        Column {
            VersionInfoText(versionInfoText = versionInfoText)
            IPAddressText(ipAddressText = ipAddressText)
        }
    }
}

@Composable
private fun AccelerometerControlButtons(
    onStartAccelerometerClick: () -> Unit,
    onStopAccelerometerClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onStartAccelerometerClick) {
            Text(text = "Start acc")
        }

        Button(onClick = onStopAccelerometerClick) {
            Text(text = "Stop acc")
        }
    }
}

@Composable
private fun AccelerometerChartsRow(accelerometerSample: AndroidAnalysedAccUIData?) {
    Row {
        repeat(times = 4) {
            Column {
                repeat(times = 3) {
                    AccelerometerChart(sample = accelerometerSample?.analysedSample)
                }
            }
        }
    }
}

@Composable
private fun AccelerometerStateView(state: SystemModuleState) {
    StateInfoRow(firstLabel = "Accelerometer state: ", state = state)
}

@Composable
private fun GyroscopeStateView(state: SystemModuleState) {
    StateInfoRow(firstLabel = "Gyroscope state: ", state = state)
}

@Composable
private fun MagneticFieldStateView(state: SystemModuleState) {
    StateInfoRow(firstLabel = "MField state: ", state = state)
}

@Composable
private fun GPSStateView(state: SystemModuleState) {
    StateInfoRow(firstLabel = "GPS state: ", state = state)
}

@Composable
private fun StateInfoRow(firstLabel: String, state: SystemModuleState) {
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

@Composable
private fun AccelerometerValueView(androidAnalysedAccUIData: AndroidAnalysedAccUIData?) {
    androidAnalysedAccUIData?.let { data ->
        ValueInfoRow(firstLabel = "Accelerometer: ", value = AnnotatedString(data.analysedSampleString))
    }
}

@Composable
private fun GyroscopeValueView(sensorData: SensorData?) {
    sensorData?.let { data ->
        if (data is SensorData.GyroscopeSample) {
            ValueInfoRow(
                firstLabel = "Gyroscope: ",
                value = AnnotatedString("X: ${data.x}, Y: ${data.y}, Z: ${data.z}")
            )
        }
    }
}

@Composable
private fun MagneticFieldValueView(sensorData: SensorData?) {
    sensorData?.let { data ->
        if (data is SensorData.MagneticFieldSample) {
            ValueInfoRow(
                firstLabel = "Magnetic field: ",
                value = AnnotatedString("X: ${data.x}, Y: ${data.y}, Z: ${data.z}")
            )
        }
    }
}

@Composable
private fun GPSValueView(sensorData: SensorData?) {
    sensorData?.let { data ->
        if (data is SensorData.GPSSample) {
            ValueInfoRow(
                firstLabel = "GPS position: ",
                value = AnnotatedString("Lat: ${data.latitude}, Lng: ${data.longitude}")
            )
        }
    }
}

@Composable
private fun ValueInfoRow(firstLabel: String, value: AnnotatedString) {
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
            text = value
        )
    }
}

@Composable
private fun AccelerometerChart(sample: AnalysedSample.AnalysedAccSample?) {
    val scope = rememberCoroutineScope()

    AndroidView(
        modifier = Modifier
            .height(45.dp)
            .width(100.dp),
        factory = { context ->
            AnalysedAccelerometerThreeAxisLinearChart(context = context)
        }, update = { chart ->
            sample?.let {
                scope.launch {
                    chart.updateAccelerometerChart(analysedSample = it)
                }
            }
        }
    )
}

@Composable
private fun VersionInfoText(versionInfoText: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        text = versionInfoText
    )
}

@Composable
private fun IPAddressText(ipAddressText: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        text = ipAddressText
    )
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreenContent(
        onStartAccelerometerClick = {},
        onStopAccelerometerClick = {},
        versionInfoText = "Version: 1.0, commit: ABCD, data: 1234",
        ipAddressText = "192.168.0.1",
        accelerometerState = SystemModuleState.Unknown,
        gyroscopeState = SystemModuleState.Unknown,
        magneticFieldState = SystemModuleState.Unknown,
        gpsState = SystemModuleState.Unknown,
        accelerometerSample = AndroidAnalysedAccUIData(
            AnalysedSample.AnalysedAccSample(
                AnalysedValue(1.20f, AnalysisResult.Normal),
                AnalysedValue(1.25f, AnalysisResult.Normal),
                AnalysedValue(1.30f, AnalysisResult.Normal),
                1L
            ),
            analysedSampleString = AnnotatedString(
                text = "X: 1.20, Y: 1.25, Z: 1.30",
                spanStyle = SpanStyle(Color.Green)
            ).toString()
        ),
        gyroscopeSample = SensorData.GyroscopeSample(1.20f, 1.30f, 1.40f),
        magneticFieldSample = SensorData.MagneticFieldSample(1.50f, 1.60f, 1.70f),
        gpsSample = SensorData.GPSSample(10.20, 20.40)
    )
}