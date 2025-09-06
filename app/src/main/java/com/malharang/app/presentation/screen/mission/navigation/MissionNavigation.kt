package com.malharang.app.presentation.screen.mission.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.mission.MissionRoute
import com.malharang.app.presentation.screen.quiz.navigation.navigateToQuizStart

fun NavController.navigateToMission(navOptions: NavOptions) {
    navigate(MainTabRoute.Mission, navOptions)
}

fun NavGraphBuilder.missionNavGraph(
    modifier: Modifier = Modifier,
    navigateToChat: () -> Unit,
    navController: NavController
) {
    composable<MainTabRoute.Mission> {
        MissionRoute(
            modifier = modifier,
            navigateToQuizStart = { navController.navigateToQuizStart() },
            navigateToChat = navigateToChat,

        )
    }
}
