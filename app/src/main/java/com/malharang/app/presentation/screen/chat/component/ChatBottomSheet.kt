package com.malharang.app.presentation.screen.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.model.MicState
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import timber.log.Timber

@Composable
fun ChatBottomContents(
    micState: MicState,
    chat: String,
    isVoiced: Boolean,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onVoiceClick: () -> Unit,
    onMicClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val surfaceColor = colors.greenLight30
    val blurModifier = modifier.drawBehind {
        val gradientHeight = size.height
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, surfaceColor),
            startY = -gradientHeight,
            endY = 0f,
        )

        drawRect(
            brush = gradientBrush,
            topLeft = Offset(x = 0f, y = -gradientHeight),
            size = Size(width = size.width, height = gradientHeight),
        )
    }


    Row(
        modifier = blurModifier
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if (!isVoiced) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colors.greenLight)
                    .clickable { onVoiceClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chat_voice_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colors.green),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            ChatTextField(
                chat = chat,
                onTextChanged = { onTextChanged(it) },
                onSendClick = { onSendClick() },
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                MicAnimationButton(
                    micState = micState,
                    onClick = {
                        onMicClick()
                    }
                )
            }
        }
    }
}


@Preview
@Composable
private fun ChatBottomSheetPreview() {
    MalHaRangTheme {
        ChatBottomContents(
            chat = "How to Order at a Coffe Shop",
            onTextChanged = {},
            onSendClick = {},
            onVoiceClick = {},
            micState = MicState.Idle,
            isVoiced = false,
            onMicClick = {}
        )
    }
}