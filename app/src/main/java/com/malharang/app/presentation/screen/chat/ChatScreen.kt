package com.malharang.app.presentation.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.malharang.app.core.util.toast
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.component.ChatBottomContents
import com.malharang.app.presentation.screen.chat.component.ChatBubble
import com.malharang.app.presentation.screen.chat.component.ChatTopBar
import com.malharang.app.presentation.screen.chat.sideeffect.ChatIntent
import com.malharang.app.presentation.screen.chat.sideeffect.ChatSideEffect
import com.malharang.app.presentation.screen.chat.sideeffect.ChatState
import com.malharang.app.presentation.screen.chat.sideeffect.ChatUiState
import com.malharang.app.presentation.screen.chat.sideeffect.MicState
import com.malharang.app.presentation.screen.chat.sideeffect.uiState
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChatRoute(
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val micState by viewModel.micState.collectAsStateWithLifecycle()

    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            context.toast(it)
            viewModel.clearToastErrorMessage()
        }
    }

    LaunchedEffect(state.chatList.size) {
        if (state.chatList.isNotEmpty()) {
            listState.animateScrollToItem(state.chatList.lastIndex + 1)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatSideEffect.ShowToast -> {
                    context.toast("\uD83D\uDCBE Saved!")
                }
            }
        }
    }

    val imeHeight = remember { mutableIntStateOf(0) }
    val ime = WindowInsets.ime
    val localDensity = LocalDensity.current
    LaunchedEffect(Unit) {
        val keyboardFlow = snapshotFlow {
            ime.getBottom(localDensity)
        }

        keyboardFlow.collect { keyboardHeight ->
            if (keyboardHeight > 0) {
                if (imeHeight.intValue < keyboardHeight) {
                    listState.scrollBy((keyboardHeight - imeHeight.intValue).toFloat())
                }
                imeHeight.intValue = keyboardHeight
            }
        }
    }

    val permissionState = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    ChatScreen(
        state = state,
        listState = listState,
        micState = micState,
        micClick = { viewModel.onMicClicked() },
        onIntent = viewModel::onIntent,
        onBackClick = onBackClick,
        onTranslateClick = { index, text, isBookmark ->
            viewModel.getTranslate(index = index, text = text, isArchive = isBookmark)
        },
        onVoiceClick = viewModel::postTextToSpeech
    )
}

@Composable
private fun ChatScreen(
    state: ChatState,
    listState: LazyListState,
    micState: MicState,
    micClick: () -> Unit,
    onVoiceClick: (Int, String) -> Unit,
    onIntent: (ChatIntent) -> Unit,
    onTranslateClick: (Int, String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.greenLight30)
            .windowInsetsPadding(WindowInsets.systemBars)
            .imePadding()
    ) {
        ChatTopBar(
            title = state.title,
            onBackClick = onBackClick,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = colors.greenBasic20
        )

        when (state.uiState) {
            ChatUiState.INITIALIZING -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colors.greenBasic
                    )
                }
            }

            ChatUiState.EMPTY -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💬 There's no selected scenario yet.",
                        style = typography.bodyMediumBold,
                        modifier = Modifier
                            .background(
                                color = colors.green,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            ChatUiState.CHATTING -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                    state = listState
                ) {
                    item {
                        Text(
                            text = "Malssi",
                            style = typography.bodySmall,
                            modifier = Modifier
                                .padding(top = 30.dp, bottom = 5.dp)
                        )
                    }

                    itemsIndexed(state.chatList) { index, chat ->
                        ChatBubble(
                            text = chat.text,
                            sender = chat.sender,
                            translatedText = chat.translatedText,
                            isTranslating = chat.isTranslating,
                            onTranslateClick = {
                                onTranslateClick(index, chat.text, false)
                            },
                            onBookmarkClick = {
                                onTranslateClick(index, chat.text, true)
                            },
                            onVoiceClick = { onVoiceClick(index, chat.text) },
                            isSoundPlaying = chat.isSoundPlaying,
                            isTranslationVisible = chat.isTranslationVisible
                        )
                    }

                    if (state.isLoading) {
                        item {
                            ChatBubble(
                                text = "...",
                                sender = SenderType.BOT
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

                ChatBottomContents(
                    chat = state.input,
                    onTextChanged = { onIntent(ChatIntent.OnInputChanged(it)) },
                    onSendClick = { onIntent(ChatIntent.SendMessage(state.input)) },
                    modifier = Modifier,
                    onVoiceClick = { onIntent(ChatIntent.OnVoiceClick) },
                    isVoiced = state.isVoiced,
                    micState = micState,
                    onMicClick = micClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    MalHaRangTheme {
        ChatScreen(
            state = ChatState(),
            onIntent = {},
            listState = rememberLazyListState(),
            onTranslateClick = { _, _, _ -> },
            micState = MicState.Idle,
            micClick = {},
            onVoiceClick = { _, _ -> }
        )
    }
}
