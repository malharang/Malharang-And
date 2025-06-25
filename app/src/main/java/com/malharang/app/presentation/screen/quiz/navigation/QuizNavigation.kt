package com.malharang.app.presentation.screen.quiz.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.malharang.app.core.navigation.QuizRoute
import com.malharang.app.presentation.screen.main.MainTab
import com.malharang.app.presentation.screen.mission.navigation.navigateToMission
import com.malharang.app.presentation.screen.quiz.QuizPlayRoute
import com.malharang.app.presentation.screen.quiz.QuizResultRoute
import com.malharang.app.presentation.screen.quiz.QuizStartRoute
import com.malharang.app.presentation.screen.quiz.QuizTypeRoute

fun NavController.navigateToQuiz(route: QuizRoute) {
    navigate(route)
}

fun NavController.navigateToQuizStart() {
    navigateToQuiz(QuizRoute.QuizStart)
}

fun NavGraphBuilder.quizNavGraph(
    padding: PaddingValues,
    navController: NavController
) {
    composable<QuizRoute.QuizStart> {
        QuizStartRoute(
            padding = padding,
            navigateToQuizType = {
                navController.navigateToQuiz(QuizRoute.QuizType(scenarioId = 123))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable<QuizRoute.QuizType> { backStackEntry ->
        val item = backStackEntry.toRoute<QuizRoute.QuizType>()
        QuizTypeRoute(
            padding = padding,
            navigateToQuizPlay = { selectedType ->
                navController.navigateToQuiz(
                    QuizRoute.QuizPlay(
                        scenarioId = item.scenarioId,
                        type = selectedType
                    )
                )
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable<QuizRoute.QuizPlay> { backStackEntry ->
        val item = backStackEntry.toRoute<QuizRoute.QuizPlay>()
        QuizPlayRoute(
            padding = padding,
            scenarioId = item.scenarioId,
            type = item.type,
            navigateToQuizResult = {
                navController.navigateToQuiz(QuizRoute.QuizResult(countResult = it))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable<QuizRoute.QuizResult> { backStackEntry ->
        val item = backStackEntry.toRoute<QuizRoute.QuizResult>()
        QuizResultRoute(
            countResult = item.countResult,
            navigateToQuizStart = {
                navController.navigateToQuiz(QuizRoute.QuizStart)
            },
            navigateToMission = {
                navController.navigateToMission(
                    navOptions = navOptions {
                        popUpTo(MainTab.HOME.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                )
            },
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}
