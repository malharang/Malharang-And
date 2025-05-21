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
    val labelMedium: TextStyle,
)

val defaultMalHaRangTypography = MalHaRangTypography(

    labelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_medium)),
        fontWeight = FontWeight(500),
        )
)

val LocalMalHaRangTypographyProvider = staticCompositionLocalOf { defaultMalHaRangTypography }