package com.malharang.app.presentation.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun ProfileRoute(
    padding: PaddingValues
) {
    ProfileScreen(padding = padding)
}

@Composable
private fun ProfileScreen(
    padding: PaddingValues
) {
    Text(
        text = "ProfileScreen"
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileScreen() {
    MalHaRangTheme {
        ProfileScreen(
            padding = PaddingValues()
        )
    }
}
