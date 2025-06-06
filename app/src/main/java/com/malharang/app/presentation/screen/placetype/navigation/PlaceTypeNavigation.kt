package com.malharang.app.presentation.screen.placetype.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.HomeRoute
import com.malharang.app.presentation.screen.placetype.PlaceTypeRoute

fun NavController.navigateToPlaceType() {
    navigate(HomeRoute.PlaceType)
}

fun NavGraphBuilder.placeTypeNavGraph(
    navController: NavController
) {
    composable<HomeRoute.PlaceType> {
        PlaceTypeRoute(
            onBackClick = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "selected_place_types",
                    navController.currentBackStackEntry?.savedStateHandle?.get<List<String>>("selected_types") ?: emptyList()
                )
                navController.popBackStack()
            },
            onTypeSelected = { selectedTypes ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "selected_types",
                    selectedTypes
                )
            }
        )
    }
}
