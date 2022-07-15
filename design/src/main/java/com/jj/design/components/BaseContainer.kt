package com.jj.design.components

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BaseContainer(appBarTitle: String, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            BaseTopAppBar(appBarTitle = appBarTitle)
        }
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewBaseContainer() {
    BaseTopAppBar(appBarTitle = "AppBarTitle")
}