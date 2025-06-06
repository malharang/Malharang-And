package com.malharang.app.presentation.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.malharang.app.presentation.model.ChatMessage
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.component.ChatBubble
import com.malharang.app.presentation.screen.chat.component.ChatTextField
import com.malharang.app.presentation.screen.chat.component.ChatTopBar
import com.malharang.app.presentation.screen.chat.util.Keyboard
import com.malharang.app.presentation.screen.chat.util.keyboardAsState
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography
import kotlinx.coroutines.launch

@Composable
fun ChatRoute(
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    ChatScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onBackClick = onBackClick,
    )
}

@Composable
private fun ChatScreen(
    state: ChatState,
    onIntent: (ChatIntent) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    missionDescription: String = "How to Order at a Coffe Shop",
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isKeyboardOpen by keyboardAsState()
    var previousChatList by remember { mutableStateOf(listOf<ChatMessage>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.greenUltraLight)
            .windowInsetsPadding(WindowInsets.systemBars)
            .imePadding()
    ) {
        ChatTopBar(
            title = missionDescription,
            onBackClick = onBackClick,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(horizontal = 16.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = colors.white
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
            state = listState,
        ) {
            item {
                Text(
                    text = "Malssi",
                    style = typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 5.dp),
                    )
            }

            items(state.chatList) { chat ->
                ChatBubble(
                    text = chat.text,
                    sender = chat.sender,
                )
            }

            if (isKeyboardOpen == Keyboard.Opened || previousChatList.size != state.chatList.size) {
                coroutineScope.launch {
                    listState.scrollToItem(state.chatList.size - 1)
                }
            }

            if (state.isLoading) {
                item {
                    ChatBubble(
                        text = "...",
                        sender = SenderType.BOT
                    )
                }
            }
            previousChatList = state.chatList
        }

        ChatTextField(
            chat = state.input,
            onTextChanged = { onIntent(ChatIntent.OnInputChanged(it)) },
            onSendClick = { onIntent(ChatIntent.SendMessage(state.input)) },
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    MalHaRangTheme {
        ChatScreen(
            missionDescription = "How to Order at a Coffe Shop",
            state = ChatState(),
            onIntent = {},
        )
    }
}
