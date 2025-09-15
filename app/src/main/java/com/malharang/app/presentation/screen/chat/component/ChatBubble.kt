package com.malharang.app.presentation.screen.chat.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.typography
import com.malharang.app.domain.model.EvaluationResponseData
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.type.EvaluationState

@Composable
fun ChatBubble(
    text: String,
    sender: SenderType,
    modifier: Modifier = Modifier,
    translatedText: String? = null,
    isTranslating: Boolean = false,
    isTranslationVisible: Boolean = false,
    isSoundPlaying: Boolean = false,
    evaluationState: EvaluationState = EvaluationState.EMPTY,
    evaluationData: EvaluationResponseData? = null,
    onTranslateClick: (String) -> Unit = {},
    onVoiceClick: (String) -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onEvaluationClick: () -> Unit = {}
) {
    val maxWidth = LocalConfiguration.current.screenWidthDp.dp * 2 / 3
    val isFromBot = sender == SenderType.BOT
    val bubbleColor = if (isFromBot) colors.greenBasic else colors.white
    val shape = RoundedCornerShape(
        topStart = if (isFromBot) 4.dp else 16.dp,
        topEnd = if (isFromBot) 16.dp else 4.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )
    val isLoading = remember { mutableStateOf(false) }
    val soundIconRes = when {
        isFromBot && isSoundPlaying -> R.drawable.ic_chat_stop_white_24
        isFromBot && !isSoundPlaying -> R.drawable.ic_chat_sound_white_24
        !isFromBot && isSoundPlaying -> R.drawable.ic_chat_stop_green_24
        else -> R.drawable.ic_chat_sound_green_24
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isFromBot) Arrangement.Start else Arrangement.End
    ) {
        if (!isFromBot) {
            EvaluationIcon(
                evaluationState = evaluationState,
                onClick = onEvaluationClick,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(
                        end = 8.dp,
                        bottom = 8.dp
                    )
            )
        }

        Column(
            modifier = Modifier
                .clip(shape)
                .background(bubbleColor)
                .then(
                    if (!isFromBot) {
                        Modifier.border(width = 1.dp, color = colors.gray, shape = shape)
                    } else {
                        Modifier
                    }
                )
                .padding(16.dp)
                .widthIn(max = maxWidth)
                .width(IntrinsicSize.Max),
            horizontalAlignment = if (isFromBot) Alignment.Start else Alignment.End
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                softWrap = true,
                style = typography.bodyMedium,
                color = if (isFromBot) colors.white else colors.black
            )

            if (translatedText != null) {
                AnimatedVisibility(visible = isTranslationVisible) {
                    Text(
                        text = if (isTranslating) "..." else translatedText,
                        textAlign = TextAlign.Start,
                        softWrap = true,
                        modifier = Modifier.padding(top = 5.dp),
                        style = typography.bodySmallPlus,
                        color = if (isFromBot) colors.white else colors.black
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 0.5.dp,
                color = if (isFromBot) colors.white20 else colors.greenBasic20
            )

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                ChatIcon(
                    icon = if (isFromBot) R.drawable.ic_chat_translate_white_24 else R.drawable.ic_chat_translate_green_24,
                    description = "Translate",
                    onClick = {
                        onTranslateClick(text)
                        if (translatedText == null) {
                            isLoading.value = true
                        }
                    }
                )
                ChatIcon(
                    icon = soundIconRes,
                    description = "Voice",
                    onClick = { onVoiceClick(text) }
                )
                ChatIcon(
                    icon = if (isFromBot) R.drawable.ic_chat_bookmark_white_24 else R.drawable.ic_chat_bookmark_green_24,
                    description = "Bookmark",
                    onClick = onBookmarkClick
                )
            }
        }
    }
}

@Composable
private fun ChatIcon(icon: Int, description: String, onClick: () -> Unit) {
    Image(
        imageVector = ImageVector.vectorResource(id = icon),
        contentDescription = description,
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}

@Composable
private fun EvaluationIcon(
    evaluationState: EvaluationState,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    loadingStrokeWidthDp: Float = 2f
) {
    val colors = MalHaRangTheme.colors

    when (evaluationState) {
        EvaluationState.EMPTY -> {
            // 아무것도 표시하지 않음
        }

        EvaluationState.Loading -> {
            CircularProgressIndicator(
                strokeWidth = loadingStrokeWidthDp.dp,
                color = colors.greenBasic,
                modifier = modifier
                    .size(16.dp)
            )
        }

        else -> {
            Icon(
                imageVector = ImageVector.vectorResource(evaluationState.icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = modifier
                    .size(24.dp)
                    .clickable { onClick() }
            )
        }
    }
}

@Preview
@Composable
private fun ChatBubblePreview() {
    MalHaRangTheme {
        Column(
            modifier = Modifier
                .background(colors.white)
        ) {
            ChatBubble(
                text = "안녕하세요! 어떤 커피를\n주문 하시겠어요?",
                sender = SenderType.BOT,
                translatedText = "hi What coffee do you want?"
            )
            ChatBubble(
                text = "아메리카노 한잔 주세요",
                sender = SenderType.USER,
                evaluationState = EvaluationState.PASS
            )
        }
    }
}
