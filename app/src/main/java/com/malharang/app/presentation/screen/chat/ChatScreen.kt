package com.malharang.app.presentation.screen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.presentation.screen.chat.component.ChatTopBar
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography
import org.w3c.dom.Text

@Composable
fun ChatRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    ChatScreen(
        padding = padding,
        onBackClick = onBackClick,
        missionDescription = "How to Order at a Coffe Shop",
    )
}

@Composable
private fun ChatScreen(
    padding: PaddingValues,
    onBackClick: () -> Unit = {},
    missionDescription: String,
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(colors.greenLight)
            .padding(horizontal = 16.dp),
    ) {
        ChatTopBar(
            title = missionDescription,
            onBackClick = onBackClick,
            )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    MalHaRangTheme {
        ChatScreen(
            padding = PaddingValues(),
            missionDescription = "How to Order at a Coffe Shop",
        )
    }
}
