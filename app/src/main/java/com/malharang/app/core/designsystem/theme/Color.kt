package com.malharang.app.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Black
val Black = Color(0xFF000000) // 100%

// White
val White = Color(0xFFFFFFFF)
val White80 = White.copy(alpha = 0.8f)
val White60 = White.copy(alpha = 0.6f)
val White20 = White.copy(alpha = 0.2f)

// Gray
val Gray = Color(0xFFF5F0E5)
val GrayDark = Color(0xFF8E8E93)

// Green
val GreenUltraLight = Color(0xFFF0FFF7)
val GreenLight = Color(0xFFD3EEDD)
val GreenLight30 = GreenLight.copy(alpha = 0.3f)
val GreenDark = Color(0xFF006358)
val GreenProfileBackgroundBottom = Color(0xFFEFF9E9)
val GreenProfileBackgroundTop = Color(0xFFFCFEF8)
val Green = Color(0xFF00AC6D)
val GreenBasic = Color(0xFF00AC6D)
val GreenBasic20 = GreenBasic.copy(alpha = 0.2f)

// Android
val AndSysGray = Color(0xFF858585)
val AndSysWhite = Color(0xFFF6F6F8)

// Tint
val GreenTint = Color(0xFF22C55E)

// Transparent
val Transparent = Color.Transparent

@Immutable
data class MalHaRangColors(
    val black: Color,
    val white: Color,
    val white80: Color,
    val white60: Color,
    val white20: Color,
    val gray: Color,
    val greenProfileBackgroundBottom: Color,
    val greenProfileBackgroundTop: Color,
    val greenBasic20: Color,
    val greenBasic: Color,
    val grayDark: Color,
    val greenUltraLight: Color,
    val greenLight: Color,
    val greenLight30: Color,
    val greenDark: Color,
    val green: Color,
    val andSysGray: Color,
    val andSysWhite: Color,
    val greenTint: Color,
    val transparent: Color
)

val defaultMalHaRangColors = MalHaRangColors(
    black = Black,
    white = White,
    white80 = White80,
    white60 = White60,
    white20 = White20,
    gray = Gray,
    grayDark = GrayDark,
    greenProfileBackgroundBottom = GreenProfileBackgroundBottom,
    greenProfileBackgroundTop = GreenProfileBackgroundTop,
    greenBasic20 = GreenBasic20,
    greenBasic = GreenBasic,
    greenUltraLight = GreenUltraLight,
    greenLight = GreenLight,
    greenLight30 = GreenLight30,
    greenDark = GreenDark,
    green = Green,
    andSysGray = AndSysGray,
    andSysWhite = AndSysWhite,
    greenTint = GreenTint,
    transparent = Transparent
)

val LocalMalHaRangColorsProvider = staticCompositionLocalOf { defaultMalHaRangColors }
