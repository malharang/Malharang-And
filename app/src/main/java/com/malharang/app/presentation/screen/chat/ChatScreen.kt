package com.malharang.app.presentation.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.presentation.model.SenderType
import com.malharang.app.presentation.screen.chat.component.ChatBubble
import com.malharang.app.presentation.screen.chat.component.ChatTextField
import com.malharang.app.presentation.screen.chat.component.ChatTopBar
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@Composable
fun ChatRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    ChatScreen(
        onBackClick = onBackClick,
        missionDescription = "How to Order at a Coffe Shop",
    )
}

@Composable
private fun ChatScreen(
    onBackClick: () -> Unit = {},
    missionDescription: String,
) {
    var chat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
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

        Text(
            text = "Malssi",
            style = typography.bodySmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 30.dp, bottom = 5.dp),

            )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
        ) {
            item {
                ChatBubble(
                    text = "안녕하세요! 어떤 커피를\n주문 하시겠어요?",
                    sender = SenderType.BOT,
                )
            }
            item {
                ChatBubble(
                    text = "아메리카노 한잔 주세요.",
                    sender = SenderType.USER,
                )
            }
            item {
                ChatBubble(
                    text = "안녕하세요! 어떤 커피를\n주문 하시겠어요?",
                    sender = SenderType.BOT,
                )
            }
        }

        ChatTextField(
            chat = chat,
            onTextChanged = { chat = it },
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    MalHaRangTheme {
        ChatScreen(
            missionDescription = "How to Order at a Coffe Shop",
        )
    }
}
