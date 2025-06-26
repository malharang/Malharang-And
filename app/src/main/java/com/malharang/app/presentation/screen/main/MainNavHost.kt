package com.malharang.app.presentation.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.malharang.app.presentation.screen.chat.navigation.chatNavGraph
import com.malharang.app.presentation.screen.home.navigation.homeNavGraph
import com.malharang.app.presentation.screen.home.navigation.navigateToHome
import com.malharang.app.presentation.screen.mission.navigation.missionNavGraph
import com.malharang.app.presentation.screen.placetype.navigation.placeTypeNavGraph
import com.malharang.app.presentation.screen.profile.navigation.profileNavGraph
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.white)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(padding, navigator.navController)

            chatNavGraph(
                navController = navigator.navController
            )

            missionNavGraph(padding)

            profileNavGraph(padding)

            placeTypeNavGraph(
                padding = padding,
                navigateToUp = navigator::navigateUp,
                navigateToHome = { placeType ->
                    val navOptions = navOptions {
                        navigator.navController.currentDestination?.route?.let {
                            popUpTo(it) {
                                inclusive = true
                            }
                        }
                    }
                    navigator.navController.navigateToHome(placeType, navOptions)
                }
            )
        }
    }
}
