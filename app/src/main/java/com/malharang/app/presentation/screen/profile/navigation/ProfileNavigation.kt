package com.malharang.app.presentation.screen.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.profile.ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions) {
    navigate(MainTabRoute.Profile, navOptions)
}

fun NavGraphBuilder.profileNavGraph(
    padding: PaddingValues
) {
    composable<MainTabRoute.Profile> {
        ProfileRoute(padding = padding)
    }
}
