package com.malharang.app.presentation.screen.chat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun ChatRoute(
    padding: PaddingValues
) {
    ChatScreen(padding = padding)
}

@Composable
private fun ChatScreen(
    padding: PaddingValues
) {
    Text(
        text = "ChatScreen"
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    MalHaRangTheme {
        ChatScreen(
            padding = PaddingValues()
        )
    }
}
