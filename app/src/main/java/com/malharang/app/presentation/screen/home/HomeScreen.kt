package com.malharang.app.presentation.screen.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun HomeRoute(
    padding: PaddingValues,
) {
    HomeScreen(padding = padding)
}


@Composable
private fun HomeScreen(
    padding: PaddingValues,
) {
    Text("HomeScreen")
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    MalHaRangTheme {
        HomeScreen(padding = PaddingValues())
    }
}