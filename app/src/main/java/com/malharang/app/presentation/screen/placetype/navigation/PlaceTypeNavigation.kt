package com.malharang.app.presentation.screen.placetype.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.HomeRoute
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.home.HomeRoute
import com.malharang.app.presentation.screen.placetype.PlaceTypeRoute


fun NavController.navigateToPlaceType() {
    navigate(HomeRoute.PlaceType)
}

fun NavGraphBuilder.placeTypeNavGraph(
    padding: PaddingValues,
    navController: NavController,
) {
    composable<HomeRoute.PlaceType> {
        PlaceTypeRoute(
            padding = padding,
            onBackClick = { navController.popBackStack() }
        )
    }
}