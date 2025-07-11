package com.malharang.app.core.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

inline fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

/**
 * Throttle 처리 함수
 */
inline fun Modifier.throttledNoRippleClickable(
    throttleTime: Long = 100L,
    coroutineScope: CoroutineScope,
    crossinline onClick: () -> Unit
): Modifier = composed {
    var isClickable by remember { mutableStateOf(true) }

    noRippleClickable {
        if (isClickable) {
            isClickable = false
            coroutineScope.launch {
                delay(throttleTime)
                onClick()
                isClickable = true
            }
        }
    }
}
