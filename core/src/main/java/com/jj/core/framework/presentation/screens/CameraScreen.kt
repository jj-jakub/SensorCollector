package com.jj.core.framework.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jj.core.R
import com.jj.core.framework.presentation.viewmodels.CameraScreenViewModel
import com.jj.design.CameraPreview
import com.jj.design.components.BaseContainer
import org.koin.androidx.compose.getViewModel

@Composable
fun CameraScreen(
    cameraScreenViewModel: CameraScreenViewModel = getViewModel()
) {
    CameraScreenContent(
        registerCameraPreview = cameraScreenViewModel::registerCameraPreview,
        onTakePhotoClick = cameraScreenViewModel::onTakePhotoClick
    )
}

@Composable
private fun CameraScreenContent(
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
            TakePhotoButton(onTakePhotoClick = onTakePhotoClick)
        }
    }
}

@Composable
fun TakePhotoButton(onTakePhotoClick: () -> Unit) {
    Button(
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
        registerCameraPreview = {},
        onTakePhotoClick = {}
    )
}