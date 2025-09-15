package com.malharang.app.presentation.screen.placetype.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malharang.app.core.common.navigation.Route
import com.malharang.app.presentation.screen.placetype.PlaceTypeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPlaceType() {
    navigate(PlaceType)
}

fun NavGraphBuilder.placeTypeNavGraph(
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable<PlaceType> {
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

@Serializable
data object PlaceType : Route
