package com.malharang.app.presentation.screen.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.common.navigation.MainTabRoute
import com.malharang.app.presentation.screen.chat.ChatRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToChat(
    navOptions: NavOptions? = null
) = navigate(Chat, navOptions)

fun NavGraphBuilder.chatNavGraph(
    navigateToUp: () -> Unit,
    navigateToQuiz: () -> Unit
) {
    composable<Chat> {
        ChatRoute(
            onBackClick = navigateToUp,
            navigateToQuiz = navigateToQuiz
        )
    }
}

@Serializable
data object Chat : MainTabRoute
