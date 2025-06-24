/*package com.malharang.app.presentation.screen.quiz.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.ChatRoute
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.home.HomeRoute
import com.malharang.app.presentation.screen.placetype.navigation.navigateToPlaceType

fun NavController.navigateToQuiz(navOptions: NavOptions) {
    navigate(ChatRoute.Quiz, navOptions)
}

fun NavGraphBuilder.quizNavGraph(
    padding: PaddingValues,
    navController: NavController
) {
    composable<ChatRoute.Quiz> {
        QuizRoute(
            padding = padding,
            navController = navController,
            navigateToPlaceType = { navController.navigateToPlaceType() }
        )
    }
}
*/
