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
