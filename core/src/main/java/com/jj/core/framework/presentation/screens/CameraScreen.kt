package com.jj.core.framework.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jj.core.R
import com.jj.core.domain.status.camera.CameraStatus
import com.jj.core.framework.presentation.viewmodels.CameraScreenViewModel
import com.jj.design.CameraPreview
import com.jj.design.components.BaseContainer
import org.koin.androidx.compose.getViewModel

@Composable
fun CameraScreen(
    cameraScreenViewModel: CameraScreenViewModel = getViewModel()
) {

    val takePhotoButtonActive by cameraScreenViewModel.takePhotoButtonActive.collectAsState()
    val cameraStatus by cameraScreenViewModel.cameraStatus.collectAsState()

    CameraScreenContent(
        takePhotoButtonActive = takePhotoButtonActive,
        cameraStatus = cameraStatus,
        registerCameraPreview = cameraScreenViewModel::registerCameraPreview,
        onTakePhotoClick = cameraScreenViewModel::onTakePhotoClick
    )
}

@Composable
private fun CameraScreenContent(
    takePhotoButtonActive: Boolean,
    cameraStatus: CameraStatus,
    registerCameraPreview: (androidx.camera.core.Preview) -> Unit,
    onTakePhotoClick: () -> Unit
) {
    BaseContainer(
        appBarTitle = stringResource(id = R.string.camera)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CameraPreview(registerCameraPreview = registerCameraPreview)

            Column {
                CameraStatusLabel(cameraStatus)
                TakePhotoButton(takePhotoButtonActive = takePhotoButtonActive, onTakePhotoClick = onTakePhotoClick)
            }
        }
    }
}

@Composable
fun CameraStatusLabel(cameraStatus: CameraStatus) {
    Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = cameraStatus.toString(),
        color = when (cameraStatus) {
            CameraStatus.Available -> Color.Green
            CameraStatus.Working -> Color.Yellow
        }
    )
}

@Composable
fun TakePhotoButton(takePhotoButtonActive: Boolean, onTakePhotoClick: () -> Unit) {
    Button(
        enabled = takePhotoButtonActive,
        modifier = Modifier.fillMaxWidth(),
        onClick = onTakePhotoClick
    ) {
        Text(text = stringResource(id = R.string.take_photo))
    }
}

@Preview
@Composable
fun PreviewCameraScreenContent() {
    CameraScreenContent(
        takePhotoButtonActive = true,
        registerCameraPreview = {},
        onTakePhotoClick = {},
        cameraStatus = CameraStatus.Available
    )
}