package com.malharang.app.presentation.screen.placetype.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier
) {
    composable<HomeRoute.PlaceType> {
        BackHandler {
            navigateToUp()
        }
        PlaceTypeRoute(
            modifier = modifier,
            onHomeNavigate = { selected ->
                navController.previousBackStackEntry?.savedStateHandle?.set("selected_place_type", selected)
                navigateToHome()
            },
            onBackButtonClick = navigateToUp
        )
    }
}
