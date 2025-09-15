package com.malharang.app.presentation.screen.quiz.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.malharang.app.core.common.navigation.Route
import com.malharang.app.core.util.sharedViewModel
import com.malharang.app.presentation.screen.quiz.QuizPlayRoute
import com.malharang.app.presentation.screen.quiz.QuizResultRoute
import com.malharang.app.presentation.screen.quiz.QuizStartRoute
import com.malharang.app.presentation.screen.quiz.QuizTypeRoute
import com.malharang.app.presentation.screen.quiz.QuizViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToQuiz(navOptions: NavOptions? = null) =
    navigate(Quiz, navOptions)

fun NavController.navigateToQuizStart(navOptions: NavOptions? = null) =
    navigate(QuizStart, navOptions)

fun NavController.navigateToQuizType(navOptions: NavOptions? = null) =
    navigate(QuizType, navOptions)

fun NavController.navigateToQuizPlay(navOptions: NavOptions? = null) =
    navigate(QuizPlay, navOptions)

fun NavController.navigateToQuizResult(navOptions: NavOptions? = null) =
    navigate(QuizResult, navOptions)

fun NavGraphBuilder.quizNavGraph(
    navController: NavHostController,
    navigateToUp: () -> Unit,
    navigateToMission: () -> Unit,
    modifier: Modifier = Modifier
) {
    navigation<Quiz>(
        startDestination = QuizStart
    ) {
        composable<QuizStart> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizStartRoute(
                navigateToQuizType = navController::navigateToQuizType,
                viewModel = viewModel
            )
        }

        composable<QuizType> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizTypeRoute(
                navigateToUp = navigateToUp,
                navigateToQuizPlay = navController::navigateToQuizPlay,
                viewModel = viewModel
            )
        }

        composable<QuizPlay> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizPlayRoute(
                navigateToUp = navigateToUp,
                navigateToQuizResult = {
                    navController.navigateToQuizResult(
                        navOptions = navOptions {
                            popUpTo<QuizResult> {
                                inclusive = true
                            }
                        }
                    )
                },
                viewModel = viewModel
            )
        }

        composable<QuizResult> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<QuizViewModel>(navController)
            QuizResultRoute(
                navigateToQuizStart = {
                    navController.navigateToQuizStart(
                        navOptions = navOptions {
                            popUpTo<QuizStart> {
                                inclusive = true
                            }
                        }
                    )
                },
                navigateToMission = navigateToMission,
                viewModel = viewModel
            )
        }
    }
}

@Serializable
data object Quiz : Route

@Serializable
data object QuizStart : Route

@Serializable
data object QuizType : Route

@Serializable
data object QuizPlay : Route

@Serializable
data object QuizResult : Route
