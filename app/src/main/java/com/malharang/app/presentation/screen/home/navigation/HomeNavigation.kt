package com.malharang.app.presentation.screen.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.home.HomeRoute
import com.malharang.app.presentation.screen.placetype.navigation.navigateToPlaceType

fun NavController.navigateToHome(
    placeType: String? = null,
    navOptions: NavOptions) {
    navigate(route = MainTabRoute.Home(placeType),
        navOptions = navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    navController: NavController
) {
    composable<MainTabRoute.Home> {
        HomeRoute(
            padding = padding,
            navigateToPlaceType = navController::navigateToPlaceType
        )
    }
}
