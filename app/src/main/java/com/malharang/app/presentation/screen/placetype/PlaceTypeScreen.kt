package com.malharang.app.presentation.screen.placetype

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.malharang.app.ui.theme.MalHaRangTheme


@Composable
fun PlaceTypeRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    PlaceTypeScreen(padding = padding)
}

@Composable
private fun PlaceTypeScreen(
    padding: PaddingValues
) {

}

@Preview(showBackground = true)
@Composable
private fun PreviewPlaceTypeScreen() {
    MalHaRangTheme {
        PlaceTypeScreen(
            padding = PaddingValues()
        )
    }
}
