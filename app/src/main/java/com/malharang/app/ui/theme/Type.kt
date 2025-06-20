package com.malharang.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.malharang.app.R

@Immutable
data class MalHaRangTypography(
    val bodySmall: TextStyle,
    val bodySmallPlus: TextStyle,
    val bodyMedium: TextStyle,
    val bodyMediumBold: TextStyle,
    val titleLarge: TextStyle
)

val defaultMalHaRangTypography = MalHaRangTypography(

    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_medium)),
        fontWeight = FontWeight.Medium
    ),
    bodySmallPlus = TextStyle(
        fontSize = 16.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_medium)),
        fontWeight = FontWeight.Medium
    ),
    bodyMedium = TextStyle(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_medium)),
        fontWeight = FontWeight.Medium
    ),
    bodyMediumBold = TextStyle(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_medium)),
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_medium)),
        fontWeight = FontWeight.ExtraBold
    )
)

val LocalMalHaRangTypographyProvider = staticCompositionLocalOf { defaultMalHaRangTypography }
