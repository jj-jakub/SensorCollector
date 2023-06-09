package com.jj.core.framework.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jj.core.R
import com.jj.design.CameraPreview
import com.jj.design.components.BaseContainer
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    settingsScreenViewModel: SettingsScreenViewModel = getViewModel()
) {

    SettingsScreenContent(settingsScreenViewModel::registerCameraPreview)
}

@Composable
fun SettingsScreenContent(
    registerCameraPreview: (androidx.camera.core.Preview) -> Unit,
) {
    BaseContainer(
        appBarTitle = stringResource(R.string.settings)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("S")
            CameraPreview(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(8.dp)
                    .background(Color.Gray),
                registerCameraPreview = registerCameraPreview
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreenContent() {
    SettingsScreenContent {}
}