package com.malharang.app.presentation.screen.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.common.navigation.Route
import com.malharang.app.presentation.screen.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSplash(navOptions: NavOptions? = null) =
    navigate(Splash, navOptions)

fun NavGraphBuilder.splashGraph(
    navigateToHome: () -> Unit
) {
    composable<Splash> {
        SplashRoute(
            navigateToHome
        )
    }
}

@Serializable
data object Splash : Route
