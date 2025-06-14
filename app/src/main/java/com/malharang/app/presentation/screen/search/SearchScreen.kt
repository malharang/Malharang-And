package com.malharang.app.presentation.screen.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    padding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel()
) {
    BackHandler {
        onBackClick()
    }

    SearchScreen(
        padding = padding,
        onBackClick = onBackClick,
    )

}

@Composable
fun SearchScreen(
    padding: PaddingValues,
    onBackClick: () -> Unit
) {

}