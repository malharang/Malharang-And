package com.malharang.app.presentation.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.malharang.app.core.navigation.MainTabRoute
import com.malharang.app.presentation.screen.chat.navigation.navigateToChat
import com.malharang.app.presentation.screen.home.navigation.navigateToHome
import com.malharang.app.presentation.screen.mission.navigation.navigateToMission
import com.malharang.app.presentation.screen.profile.navigation.navigateToProfile

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTabRoute.Home

    val currentTab: MainTab?
        @Composable get() =
            when {
                else -> MainTab.find { tab -> currentDestination?.hasRoute(tab::class) == true }
            }

    var selectedConversationId: Long? = null

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(MainTab.HOME.route) {
                inclusive = false
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions = navOptions)
            MainTab.CHAT -> navController.navigateToChat(navOptions = navOptions)
            MainTab.MISSION -> navController.navigateToMission(navOptions)
            MainTab.PROFILE -> navController.navigateToProfile(navOptions)
        }
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val isMainTabRoute = MainTab.contains {
            currentDestination?.hasRoute(it::class) == true
        }

        val currentRoute = currentDestination?.route

        return isMainTabRoute && currentDestination?.hasRoute(MainTab.CHAT.route::class) != true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
