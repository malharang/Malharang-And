package com.malharang.app.presentation.screen.goal.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.HomeRoute
import com.malharang.app.presentation.screen.goal.GoalRoute

fun NavController.navigateToGoal() {
    navigate(HomeRoute.Goal)
}

fun NavGraphBuilder.goalNavGraph(
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
    navController: NavController,
    padding: PaddingValues
) {
    composable<HomeRoute.Goal> {
        BackHandler {
            navigateToUp()
        }
        GoalRoute(
            padding = padding,
            onBackClick = navigateToUp,
            onConfirmClick = { selected ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("selected_goal", selected)
                navigateToHome()
            }
        )
    }
}
