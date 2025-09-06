package com.malharang.app.presentation.screen.quiz.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.malharang.app.core.navigation.QuizRoute
import com.malharang.app.core.navigation.QuizRoute.QuizPlay
import com.malharang.app.core.navigation.QuizRoute.QuizStart
import com.malharang.app.core.navigation.QuizRoute.QuizType
import com.malharang.app.core.util.sharedViewModel
import com.malharang.app.presentation.screen.quiz.QuizPlayRoute
import com.malharang.app.presentation.screen.quiz.QuizResultRoute
import com.malharang.app.presentation.screen.quiz.QuizStartRoute
import com.malharang.app.presentation.screen.quiz.QuizTypeRoute
import com.malharang.app.presentation.screen.quiz.QuizViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToQuiz(route: QuizRoute) {
    navigate(route)
}

fun NavController.navigateToQuizStart() {
    navigateToQuiz(QuizStart)
}

fun NavController.navigateToQuizType() {
    navigateToQuiz(QuizType)
}

fun NavController.navigateToQuizPlay() {
    navigateToQuiz(QuizPlay)
}

fun NavController.navigateToQuizResult() {
    navigateToQuiz(QuizRoute.QuizResult)
}

fun NavGraphBuilder.quizNavGraph(
    navController: NavHostController,
    navigateToUp: () -> Unit,
    navigateToMission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<Quiz>(
        startDestination = QuizStart,
    ) {
        composable<QuizStart> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizStartRoute(
                navigateToUp = navigateToUp,
                navigateToQuizType = navController::navigateToQuizType,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<QuizType> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizTypeRoute(
                navigateToUp = navigateToUp,
                navigateToQuizPlay = navController::navigateToQuizPlay,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<QuizPlay> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizPlayRoute(
                navigateToUp = navigateToUp,
                navigateToQuizResult = navController::navigateToQuizResult,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<QuizRoute.QuizResult> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizResultRoute(
                navigateToUp = navigateToUp,
                navigateToQuizStart = navController::navigateToQuizStart,
                navigateToMission = navigateToMission,
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}

@Serializable
data object Quiz
