package com.malharang.app.presentation.screen.mission.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.common.navigation.MainTabRoute
import com.malharang.app.presentation.screen.mission.MissionRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMission(
    navOptions: NavOptions? = null
) = navigate(Mission, navOptions)

fun NavGraphBuilder.missionNavGraph(
    navigateToChat: () -> Unit,
    navigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Mission> {
        MissionRoute(
            modifier = modifier,
            navigateToQuiz = navigateToQuiz,
            navigateToChat = navigateToChat
        )
    }
}

@Serializable
data object Mission : MainTabRoute
