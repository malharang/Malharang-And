package com.malharang.app.presentation.screen.profile.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.malharang.app.core.common.navigation.MainTabRoute
import com.malharang.app.presentation.screen.profile.ProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToProfile(navOptions: NavOptions) {
    navigate(Profile, navOptions)
}

fun NavGraphBuilder.profileNavGraph(
    modifier: Modifier = Modifier
) {
    composable<Profile> {
        ProfileRoute(modifier = modifier)
    }
}

@Serializable
data object Profile : MainTabRoute
