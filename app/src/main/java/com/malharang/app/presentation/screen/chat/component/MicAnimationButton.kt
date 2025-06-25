package com.malharang.app.presentation.screen.chat.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Alignment
import com.malharang.app.R
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.screen.chat.sideeffect.MicState
import timber.log.Timber

@Composable
fun MicAnimationButton(
    micState: MicState,
    modifier: Modifier = Modifier,
    @RawRes animation: Int = R.raw.mic_record_button,
    stateMachineName: String = "MicStateMachine",
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.size(100.dp)
    ) {
        AndroidView(
            factory = { context ->
                RiveAnimationView(context).apply {
                    isClickable = false
                    isFocusable = false
                    isFocusableInTouchMode = false
                    setRiveResource(
                        resId = animation,
                        stateMachineName = stateMachineName,
                        alignment = Alignment.CENTER
                    )
                }
            },
            update = { view ->
                view.post {
                    micState.trigger?.let { triggerName ->
                        Timber.tag("RIVE").d("Firing trigger: $triggerName in $stateMachineName")
                        view.fireState(stateMachineName, triggerName)
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .noRippleClickable(enabled = micState != MicState.StartProcessing) {
                    onClick()
                }
        )
    }
}