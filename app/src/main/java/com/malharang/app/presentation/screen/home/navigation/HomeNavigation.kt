package com.malharang.app.presentation.screen.home.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.malharang.app.core.common.navigation.MainTabRoute
import com.malharang.app.core.common.navigation.Route
import com.malharang.app.core.util.sharedViewModel
import com.malharang.app.presentation.screen.home.GoalRoute
import com.malharang.app.presentation.screen.home.HomeViewModel
import kotlinx.serialization.Serializable
import com.malharang.app.presentation.screen.home.HomeRoute as HomeScreenRoute

fun NavController.navigateToHome(
    navOptions: NavOptions? = null,
) = navigate(Home, navOptions)

fun NavController.navigateToGoal(navOptions: NavOptions? = null) =
    navigate(Goal, navOptions)

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    navigateToChat: () -> Unit,
    navigateToPlaceType: () -> Unit,
    navigateToUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<Home>(
        startDestination = HomeMain,
    ) {
        composable<HomeMain> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)
            val savedStateHandle = backStackEntry.savedStateHandle

            val selectedPlaceType = savedStateHandle.get<String>("selected_place_type")
            val selectedGoal = savedStateHandle.get<String>("selected_goal")

            HomeScreenRoute(
                modifier = modifier,
                navigateToPlaceType = navigateToPlaceType,
                navigateToGoal = navController::navigateToGoal,
                navigateToChat = navigateToChat,
                viewModel = viewModel,
            )

            LaunchedEffect(Unit) {
                savedStateHandle.remove<String>("selected_place_type")
                savedStateHandle.remove<String>("selected_goal")
            }
        }

        composable<Goal> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)

            GoalRoute(
                modifier = modifier,
                navigateToUp = navigateToUp,
                viewModel = viewModel,
            )
        }
    }
}

@Serializable
data object Home : MainTabRoute

@Serializable
data object HomeMain : MainTabRoute

@Serializable
data object Goal : Route
