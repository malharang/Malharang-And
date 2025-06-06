package com.malharang.app.presentation.screen.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.home.HomeRoute
import com.malharang.app.presentation.screen.placetype.navigation.navigateToPlaceType

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    navController: NavController
) {
    composable<MainTabRoute.Home> {
        HomeRoute(
            padding = padding,
            navController = navController,
            navigateToPlaceType = { navController.navigateToPlaceType() }
        )
    }
}
