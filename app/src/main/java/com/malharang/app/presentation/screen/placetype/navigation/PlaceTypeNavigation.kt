package com.malharang.app.presentation.screen.placetype.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.HomeRoute
import com.malharang.app.presentation.screen.placetype.PlaceTypeRoute

fun NavController.navigateToPlaceType() {
    navigate(HomeRoute.PlaceType)
}

fun NavGraphBuilder.placeTypeNavGraph(
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
    navController: NavController,
    padding: PaddingValues
) {
    composable<HomeRoute.PlaceType> {
        BackHandler {
            navigateToUp()
        }
        PlaceTypeRoute(
            padding = padding,
            onHomeNavigate = { selected ->
                navController.previousBackStackEntry?.savedStateHandle?.set("selected_place_type", selected)
                navigateToHome()
            },
            onBackButtonClick = navigateToUp
        )
    }
}
