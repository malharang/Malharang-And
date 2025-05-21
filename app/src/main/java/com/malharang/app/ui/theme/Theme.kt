package com.malharang.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object MalHaRangTheme {
    val colors: MalHaRangColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMalHaRangColorsProvider.current

    val typography: MalHaRangTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMalHaRangTypographyProvider.current
}

@Composable
fun ProvideMalHaRangColorsAndTypography(
    colors: MalHaRangColors,
    typography: MalHaRangTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMalHaRangColorsProvider provides colors,
        LocalMalHaRangTypographyProvider provides typography,
        content = content
    )
}

@Composable
fun MalHaRangTheme(
    content: @Composable () -> Unit
) {
    ProvideMalHaRangColorsAndTypography(
        colors = defaultMalHaRangColors,
        typography = defaultMalHaRangTypography,
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.run {
                    WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars
                }
            }
        }

        MaterialTheme(
            content = content
        )
    }
}
