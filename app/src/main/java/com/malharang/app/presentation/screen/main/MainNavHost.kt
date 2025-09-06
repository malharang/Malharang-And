package com.malharang.app.presentation.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.malharang.app.presentation.screen.chat.navigation.chatNavGraph
import com.malharang.app.presentation.screen.chat.navigation.navigateToChat
import com.malharang.app.presentation.screen.goal.navigation.goalNavGraph
import com.malharang.app.presentation.screen.home.navigation.homeNavGraph
import com.malharang.app.presentation.screen.home.navigation.navigateToHome
import com.malharang.app.presentation.screen.mission.navigation.missionNavGraph
import com.malharang.app.presentation.screen.mission.navigation.navigateToMission
import com.malharang.app.presentation.screen.placetype.navigation.placeTypeNavGraph
import com.malharang.app.presentation.screen.profile.navigation.profileNavGraph
import com.malharang.app.presentation.screen.quiz.navigation.quizNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeNavGraph(
            navController = navigator.navController,
            modifier = modifier,
            navigateToChat = {
                val navOptions = navOptions {
                    popUpTo(MainTab.CHAT.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                navigator.navController.navigateToChat(navOptions)
            }
        )

        chatNavGraph(
            navController = navigator.navController
        )

        missionNavGraph(
            navController = navigator.navController,
            navigateToChat = {
                val navOptions = navOptions {
                    popUpTo(MainTab.CHAT.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                navigator.navController.navigateToChat(navOptions)
            },
        )

        profileNavGraph(modifier)

        placeTypeNavGraph(
            navigateToUp = navigator::navigateUp,
            navigateToHome = {
                val navOptions = navOptions {
                    popUpTo(MainTab.HOME.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
                navigator.navController.navigateToHome(navOptions)
            },
            navController = navigator.navController
        )

        goalNavGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
            navigateToHome = {
                val navOptions = navOptions {
                    popUpTo(MainTab.HOME.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
                navigator.navController.navigateToHome(navOptions)
            },
            modifier = modifier
        )

        quizNavGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
            navigateToMission = navigator.navController::navigateToMission,
            modifier = modifier,
        )
    }
}
