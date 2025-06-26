package com.malharang.app.presentation.screen.placetype.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.HomeRoute
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.placetype.PlaceTypeRoute

fun NavController.navigateToPlaceType() {
    navigate(HomeRoute.PlaceType)
}

fun NavGraphBuilder.placeTypeNavGraph(
    navigateToUp: () -> Unit,
    navigateToHome: (String) -> Unit,
    padding: PaddingValues,
) {
    composable<HomeRoute.PlaceType> {
        BackHandler {
            navigateToUp()
        }
        PlaceTypeRoute(
            padding = padding,
            onHomeNavigate = navigateToHome,
            onBackButtonClick = navigateToUp,
        )
    }
}
