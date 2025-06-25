package com.malharang.app.core.navigation

import kotlinx.serialization.Serializable

sealed interface MainTabRoute {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Chat : MainTabRoute

    @Serializable
    data object Mission : MainTabRoute

    @Serializable
    data object Profile : MainTabRoute
}

sealed interface HomeRoute {
    @Serializable
    data object PlaceType : HomeRoute

}

sealed interface QuizRoute {
    @Serializable
    data object QuizStart : QuizRoute

    @Serializable
    data class QuizType(
        val scenarioId: Int,
    ): QuizRoute

    @Serializable
    data class QuizPlay(
        val scenarioId: Int,
        val type: String,
    ): QuizRoute

    @Serializable
    data class QuizResult(
        val countResult: String,
    ): QuizRoute
}
