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

    @Serializable
    data object Goal : HomeRoute

}

sealed interface QuizRoute {
    @Serializable
    data object QuizStart : QuizRoute

    @Serializable
    data object QuizType: QuizRoute

    @Serializable
    data object QuizPlay: QuizRoute

    @Serializable
    data object QuizResult: QuizRoute
}
