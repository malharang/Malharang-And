package com.malharang.app.presentation.screen.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.HomeRoute
import com.malharang.app.presentation.screen.placetype.PlaceTypeRoute
import com.malharang.app.presentation.screen.search.SearchRoute

fun NavController.navigateToSearch() {
    navigate(HomeRoute.Search)
}

fun NavGraphBuilder.searchNavGraph(
    navController: NavController,
    padding: PaddingValues
) {
    composable<HomeRoute.Search> {
        SearchRoute(
            padding = padding,
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}
