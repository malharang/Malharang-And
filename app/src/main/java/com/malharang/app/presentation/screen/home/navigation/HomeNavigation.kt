package com.malharang.app.presentation.screen.home.navigation

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
import com.malharang.app.presentation.screen.home.PlaceTypeRoute
import kotlinx.serialization.Serializable
import com.malharang.app.presentation.screen.home.HomeRoute as HomeScreenRoute

fun NavController.navigateToHome(
    navOptions: NavOptions? = null,
) = navigate(Home, navOptions)

fun NavController.navigateToGoal(navOptions: NavOptions? = null) =
    navigate(Goal, navOptions)

fun NavController.navigateToPlaceType(navOptions: NavOptions? = null) =
    navigate(PlaceType, navOptions)

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    navigateToChat: () -> Unit,
    navigateToPlaceType: () -> Unit,
    navigateToGoal: () -> Unit,
    navigateToUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<Home>(
        startDestination = HomeMain,
    ) {
        composable<HomeMain> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)

            HomeScreenRoute(
                navigateToPlaceType = navigateToPlaceType,
                navigateToGoal = navigateToGoal,
                navigateToChat = navigateToChat,
                modifier = modifier,
                viewModel = viewModel,
            )
        }

        composable<Goal> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)

            GoalRoute(
                navigateToUp = navigateToUp,
                modifier = modifier,
                viewModel = viewModel,
            )
        }

        composable<PlaceType> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<HomeViewModel>(navController)

            PlaceTypeRoute(
                navigateToUp = navigateToUp,
                modifier = modifier,
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

@Serializable
data object PlaceType : Route
