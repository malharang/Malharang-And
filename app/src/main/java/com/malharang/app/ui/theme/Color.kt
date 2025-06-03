package com.malharang.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Black
val Black = Color(0xFF000000) // 100%

// White
val White = Color(0xFFFFFFFF)
val White20 = Color(0x33FFFFFF)

// Gray
val Gray = Color(0xFFF5F0E5)
val GrayDark = Color(0xFF8E8E93)

// Green
val GreenLight = Color(0xFFD3EEDD)
val GreenDark = Color(0xFF006358)
val Green = Color(0xFF00AC6D)

// Android
val AndSysGray = Color(0xFF858585)
val AndSysWhite = Color(0xFFF6F6F8)

// Tint
val GreenTint = Color(0xFF22C55E)

@Immutable
data class MalHaRangColors(
    val black: Color,
    val white: Color,
    val gray: Color,
    val grayDark: Color,
    val greenLight: Color,
    val greenDark: Color,
    val green: Color,
    val andSysGray: Color,
    val andSysWhite: Color,
    val greenTint: Color
)

val defaultMalHaRangColors = MalHaRangColors(
    black = Black,
    white = White,
    gray = Gray,
    grayDark = GrayDark,
    greenLight = GreenLight,
    greenDark = GreenDark,
    green = Green,
    andSysGray = AndSysGray,
    andSysWhite = AndSysWhite,
    greenTint = GreenTint
)

val LocalMalHaRangColorsProvider = staticCompositionLocalOf { defaultMalHaRangColors }
