package com.malharang.app.presentation.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import com.malharang.app.presentation.screen.chat.navigation.Chat
import com.malharang.app.presentation.screen.chat.navigation.chatNavGraph
import com.malharang.app.presentation.screen.chat.navigation.navigateToChat
import com.malharang.app.presentation.screen.home.navigation.homeNavGraph
import com.malharang.app.presentation.screen.home.navigation.navigateToGoal
import com.malharang.app.presentation.screen.home.navigation.navigateToPlaceType
import com.malharang.app.presentation.screen.main.component.MainBottomBar
import com.malharang.app.presentation.screen.mission.navigation.Mission
import com.malharang.app.presentation.screen.mission.navigation.missionNavGraph
import com.malharang.app.presentation.screen.mission.navigation.navigateToMission
import com.malharang.app.presentation.screen.profile.navigation.profileNavGraph
import com.malharang.app.presentation.screen.quiz.navigation.navigateToQuiz
import com.malharang.app.presentation.screen.quiz.navigation.quizNavGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .navigationBarsPadding(),
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toImmutableList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate,
            )
        },
        containerColor = colors.white,
    ) { innerPadding ->
        MainNavHost(
            navigator = navigator,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeNavGraph(
            navController = navigator.navController,
            navigateToChat = navigator.navController::navigateToChat,
            navigateToPlaceType = navigator.navController::navigateToPlaceType,
            navigateToGoal = navigator.navController::navigateToGoal,
            navigateToUp = navigator::navigateUp,
            modifier = modifier,
        )

        chatNavGraph(
            navigateToUp = navigator::navigateUp,
            navigateToQuiz = navigator.navController::navigateToQuiz,
        )

        missionNavGraph(
            navigateToChat = {
                navigator.navController.navigateToChat(navOptions = navOptions {
                    popUpTo<Chat> {
                        inclusive = true
                    }
                })
            },
            navigateToQuiz = navigator.navController::navigateToQuiz,
            modifier = modifier,
        )

        profileNavGraph(
            modifier,
        )

        quizNavGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
            navigateToMission = {
                navigator.navController.navigateToMission(navOptions = navOptions {
                    popUpTo<Mission> {
                        inclusive = true
                    }
                })
            },
            modifier = modifier,
        )
    }
}
