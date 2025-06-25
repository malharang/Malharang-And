package com.malharang.app.presentation.screen.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.chat.ChatRoute
import com.malharang.app.presentation.screen.quiz.navigation.navigateToQuizStart

fun NavController.navigateToChat(navOptions: NavOptions) {
    navigate(MainTabRoute.Chat, navOptions)
}

fun NavGraphBuilder.chatNavGraph(
    navController: NavController
) {
    composable<MainTabRoute.Chat> {
        ChatRoute(
            onBackClick = { navController.popBackStack() },
            navigateToQuizStart = { navController.navigateToQuizStart() }
        )
    }
}
