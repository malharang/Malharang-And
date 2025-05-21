package com.malharang.app.presentation.screen.mission

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun MissionRoute(
    padding: PaddingValues,
) {
    MissionScreen(padding = padding)
}

@Composable
private fun MissionScreen(
    padding: PaddingValues,
) {
    Text(
        text = "MissionScreen",
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewMissionScreen() {
    MalHaRangTheme {
        MissionScreen(
            padding = PaddingValues(),
        )
    }
}