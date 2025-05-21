package com.malharang.app.presentation.screen.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.malharang.app.R
import com.malharang.app.core.navigation.MainTabRoute

enum class MainTab(
    @DrawableRes val inactiveIconResId: Int,
    @DrawableRes val activeIconResId: Int,
    @StringRes val title: Int,
    val route: MainTabRoute
) {
    HOME(
        inactiveIconResId = R.drawable.ic_navi_home_inactive_24,
        activeIconResId = R.drawable.ic_navi_home_active_24,
        title = R.string.navi_home_title,
        route = MainTabRoute.Home
    ),
    CHAT(
        inactiveIconResId = R.drawable.ic_navi_chat_inactive_24,
        activeIconResId = R.drawable.ic_navi_chat_inactive_24,
        title = R.string.navi_chat_title,
        route = MainTabRoute.Chat
    ),
    MISSION(
        inactiveIconResId = R.drawable.ic_navi_mission_inactive_24,
        activeIconResId = R.drawable.ic_navi_mission_active_24,
        title = R.string.navi_mission_title,
        route = MainTabRoute.Mission
    ),
    PROFILE(
        inactiveIconResId = R.drawable.ic_navi_profile_inactive_24,
        activeIconResId = R.drawable.ic_navi_profile_active_24,
        title = R.string.navi_profile_title,
        route = MainTabRoute.Profile
    );

    companion object {
        @Composable
        fun contains(predicate: @Composable (MainTabRoute) -> Boolean): Boolean {
            return MainTab.entries.map { it.route }.any { predicate(it) }
        }
    }
}
