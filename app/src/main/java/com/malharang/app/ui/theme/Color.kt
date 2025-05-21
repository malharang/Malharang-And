package com.malharang.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Black
val Black = Color(0xFF000000) // 100%

// White
val White = Color(0xFFFFFFFF)

// Android
val AndSysGray = Color(0xFF858585)
val AndSysWhite = Color(0xFFF6F6F8)

@Immutable
data class MalHaRangColors(
    val black: Color,
    val white: Color,
    val andSysGray: Color,
    val andSysWhite: Color,
)

val defaultMalHaRangColors = MalHaRangColors(
    black = Black,
    white = White,
    andSysGray = AndSysGray,
    andSysWhite = AndSysWhite,
)

val LocalMalHaRangColorsProvider = staticCompositionLocalOf { defaultMalHaRangColors }