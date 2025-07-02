package com.malharang.app.presentation.screen.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.chat.navigation.navigateToChat
import com.malharang.app.presentation.screen.goal.navigation.navigateToGoal
import com.malharang.app.presentation.screen.home.HomeRoute
import com.malharang.app.presentation.screen.main.MainTab
import com.malharang.app.presentation.screen.placetype.navigation.navigateToPlaceType

fun NavController.navigateToHome(
    navOptions: NavOptions
) {
    navigate(
        route = MainTabRoute.Home,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    navController: NavController
) {
    composable<MainTabRoute.Home> {
        val backStackEntry = it
        val savedStateHandle = backStackEntry.savedStateHandle

        val selectedPlaceType = savedStateHandle.get<String>("selected_place_type")
        val selectedGoal = savedStateHandle.get<String>("selected_goal")

        HomeRoute(
            padding = padding,
            placeTypeArg = selectedPlaceType,
            goalArg = selectedGoal,
            navigateToPlaceType = navController::navigateToPlaceType,
            navigateToGoal = navController::navigateToGoal,
            navigateToChat = {
                navController.navigateToChat(
                    navOptions = navOptions {
                        popUpTo(MainTab.CHAT.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                )
            }
        )

        LaunchedEffect(Unit) {
            savedStateHandle.remove<String>("selected_place_type")
            savedStateHandle.remove<String>("selected_goal")
        }
    }
}
