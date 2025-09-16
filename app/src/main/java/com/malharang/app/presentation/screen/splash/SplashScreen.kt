package com.malharang.app.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    navigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1200)
        navigateToHome()
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_splash_logo),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.img_splash_name),
                contentDescription = null,
                modifier = Modifier.width(120.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    MalHaRangTheme {
        SplashScreen()
    }
}
