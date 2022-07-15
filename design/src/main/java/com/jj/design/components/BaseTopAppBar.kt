package com.jj.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseTopAppBar(
    appBarTitle: String,
    elevation: Dp = 0.dp,
    leftHandContent: @Composable (Modifier) -> Unit = {},
    rightHandContent: @Composable (Modifier) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 4.dp)
    ) {
        TopAppBar(
            elevation = elevation,
            content = {
                BarContent(
                    appBarTitle, leftHandContent, rightHandContent
                )
            }
        )
    }
}

@Composable
private fun BarContent(
    appBarTitle: String,
    leftHandContent: @Composable (Modifier) -> Unit,
    rightHandContent: @Composable (Modifier) -> Unit
) {
    Row {
        Box {
            leftHandContent(
                Modifier.wrapContentWidth()
            )
        }
        Box {
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = appBarTitle,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
        Box {
            rightHandContent(
                Modifier.wrapContentWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBaseTopAppBar() {
    BaseTopAppBar(appBarTitle = "Title", elevation = 0.dp, leftHandContent = {}, rightHandContent = {})
}