package com.malharang.app.presentation.screen.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.malharang.app.R
import com.malharang.app.core.common.navigation.MainTabRoute
import com.malharang.app.core.common.navigation.Route
import com.malharang.app.presentation.screen.chat.navigation.Chat
import com.malharang.app.presentation.screen.home.navigation.HomeMain
import com.malharang.app.presentation.screen.mission.navigation.Mission
import com.malharang.app.presentation.screen.profile.navigation.Profile

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
        route = HomeMain
    ),
    CHAT(
        inactiveIconResId = R.drawable.ic_navi_chat_inactive_24,
        activeIconResId = R.drawable.ic_navi_chat_inactive_24,
        title = R.string.navi_chat_title,
        route = Chat
    ),
    MISSION(
        inactiveIconResId = R.drawable.ic_navi_mission_inactive_24,
        activeIconResId = R.drawable.ic_navi_mission_active_24,
        title = R.string.navi_mission_title,
        route = Mission
    ),
    PROFILE(
        inactiveIconResId = R.drawable.ic_navi_profile_inactive_24,
        activeIconResId = R.drawable.ic_navi_profile_active_24,
        title = R.string.navi_profile_title,
        route = Profile
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { tab ->
                predicate(tab.route)
            }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
