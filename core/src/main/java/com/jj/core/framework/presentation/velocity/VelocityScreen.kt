package com.jj.core.framework.presentation.velocity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.view.WindowManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.jj.core.framework.presentation.components.hardware.StateInfoRow
import com.jj.core.framework.presentation.components.hardware.ValueInfoRow
import com.jj.domain.monitoring.model.SystemModuleState
import org.koin.androidx.compose.getViewModel

@Composable
fun VelocityScreen(
    velocityScreenViewModel: VelocityScreenViewModel = getViewModel(),
) {
    val gpsState by velocityScreenViewModel.gpsState.collectAsState()
    val currentVelocity by velocityScreenViewModel.currentVelocity.collectAsState()
    val avrVelocity1 by velocityScreenViewModel.averageVelocity1.collectAsState()
    val avrVelocity2 by velocityScreenViewModel.averageVelocity2.collectAsState()

    val averagePathVelocity by velocityScreenViewModel.averagePathVelocity.collectAsState()
    val stackedPathDistance by velocityScreenViewModel.stackedPathDistance.collectAsState()
    val allSamplesPathDistance by velocityScreenViewModel.allSamplesPathDistance.collectAsState()
    val isPathRecording by velocityScreenViewModel.isPathRecording.collectAsState()

    KeepScreenOn()
    CheckLocationPermission(
        onPermissionGranted = velocityScreenViewModel::onPermissionGranted
    )
    VelocityScreenContent(
        gpsState = gpsState,
        currentVelocity = currentVelocity,
        avrVelocity1 = avrVelocity1,
        avrVelocity2 = avrVelocity2,
        averagePathVelocity = averagePathVelocity,
        stackedPathDistance = stackedPathDistance,
        allSamplesPathDistance = allSamplesPathDistance,
        isPathRecording = isPathRecording,
        onClearVelocitiesClick = velocityScreenViewModel::onClearVelocitiesClick,
        onStartGPSAnalysisClick = velocityScreenViewModel::onStartGPSAnalysisClick,
        onStopGPSAnalysisClick = velocityScreenViewModel::onStopGPSAnalysisClick,
        onStartPathClick = velocityScreenViewModel::startPathClick,
        onStopPathClick = velocityScreenViewModel::stopPathClick,
    )
}

@Composable
fun CheckLocationPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permission = Manifest.permission.ACCESS_FINE_LOCATION

    val hasFineLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    if (!hasFineLocationPermission) {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                if (granted) onPermissionGranted()
            }
        )

        LaunchedEffect(key1 = true) {
            launcher.launch(permission)
        }
    }
}

@Composable
private fun VelocityScreenContent(
    gpsState: SystemModuleState,
    currentVelocity: Double,
    avrVelocity1: Double,
    avrVelocity2: Double,
    averagePathVelocity: Double,
    stackedPathDistance: Double,
    allSamplesPathDistance: Double,
    isPathRecording: Boolean,
    onClearVelocitiesClick: () -> Unit,
    onStartGPSAnalysisClick: () -> Unit,
    onStopGPSAnalysisClick: () -> Unit,
    onStartPathClick: () -> Unit,
    onStopPathClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            StateInfoRow(
                firstLabel = "GPS state:",
                state = gpsState
            )

            ValueInfoRow(
                firstLabel = "Velocity km/h: ",
                value = AnnotatedString(text = currentVelocity.toString())
            )
            LargeVelocityText(velocity = currentVelocity)
            ValueInfoRow(
                firstLabel = "Avr Velocity1 (sum) km/h: ",
                value = AnnotatedString(text = avrVelocity1.toString())
            )
            LargeVelocityText(velocity = avrVelocity1)
            ValueInfoRow(
                firstLabel = "Avr Velocity2 (all samples) km/h: ",
                value = AnnotatedString(text = avrVelocity2.toString())
            )

            ValueInfoRow(
                firstLabel = "Path Recording",
                value = AnnotatedString(
                    text = isPathRecording.toString(),
                    spanStyle = SpanStyle(if (isPathRecording) Color.Green else Color.Red),
                )
            )
            ValueInfoRow(
                firstLabel = "Avr Path V km/h",
                value = AnnotatedString(text = averagePathVelocity.toString())
            )
            ValueInfoRow(
                firstLabel = "Stacked Path Distance km",
                value = AnnotatedString(text = stackedPathDistance.toString())
            )
            ValueInfoRow(
                firstLabel = "All samples Path Distance km",
                value = AnnotatedString(text = allSamplesPathDistance.toString())
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = onStopGPSAnalysisClick,
            ) {
                Text(text = "Stop GPS")
            }
            Button(
                onClick = onStartGPSAnalysisClick,
            ) {
                Text(text = "Start GPS")
            }
            Button(
                onClick = onClearVelocitiesClick,
            ) {
                Text(text = "Clear Velocities")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = onStopPathClick,
            ) {
                Text(text = "Stop Path")
            }
            Button(
                onClick = onStartPathClick,
            ) {
                Text(text = "Start Path")
            }
        }
    }
}

@Composable
private fun LargeVelocityText(velocity: Double) {
    Text(
        fontSize = 100.sp,
        text = String.format("%.2f", velocity)
    )
}

@Composable
private fun KeepScreenOn() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val window = context.findActivity()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Preview
@Composable
fun PreviewVelocityScreenContent() {
    VelocityScreenContent(
        gpsState = SystemModuleState.Working,
        currentVelocity = 55.55,
        avrVelocity1 = 0.0,
        avrVelocity2 = 0.0,
        averagePathVelocity = 0.0,
        stackedPathDistance = 0.0,
        allSamplesPathDistance = 0.0,
        isPathRecording = false,
        onClearVelocitiesClick = {},
        onStartGPSAnalysisClick = {},
        onStopGPSAnalysisClick = {},
        onStartPathClick = {},
        onStopPathClick = {},
    )
}