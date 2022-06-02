package com.jj.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YoScreen(content: String) {
    Column(
        modifier = Modifier.padding(top = 32.dp)
    ) {
        Text("Hello: $content")
    }
}