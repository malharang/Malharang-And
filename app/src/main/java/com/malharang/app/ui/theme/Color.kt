package com.malharang.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Black
val Black = Color(0xFF000000) // 100%

// White
val White = Color(0xFFFFFFFF)

// Gray
val Gray = Color(0xFFF5F0E5)

// Green
val Green1 = Color(0xFFD3EEDD)
val Green2 = Color(0xFF006358)
// Android
val AndSysGray = Color(0xFF858585)
val AndSysWhite = Color(0xFFF6F6F8)

@Immutable
data class MalHaRangColors(
    val black: Color,
    val white: Color,
    val gray: Color,
    val green1: Color,
    val green2: Color,
    val andSysGray: Color,
    val andSysWhite: Color,
)

val defaultMalHaRangColors = MalHaRangColors(
    black = Black,
    white = White,
    gray = Gray,
    green1 = Green1,
    green2 = Green2,
    andSysGray = AndSysGray,
    andSysWhite = AndSysWhite,
)

val LocalMalHaRangColorsProvider = staticCompositionLocalOf { defaultMalHaRangColors }