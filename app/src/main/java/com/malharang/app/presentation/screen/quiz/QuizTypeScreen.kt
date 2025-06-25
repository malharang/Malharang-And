package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun QuizTypeRoute(
    padding: PaddingValues,
    navigateToQuizPlay: (String) -> Unit, // "word" 또는 "sentence"
    onBackClick: () -> Unit
) {
    QuizTypeScreen(
        padding = padding,
        onTypeSelected = { type ->
            navigateToQuizPlay(type)
        },
        onBackClick = onBackClick
    )
}

@Composable
fun QuizTypeScreen(
    padding: PaddingValues,
    onTypeSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(colors.white)
            .padding(16.dp)
    ) {
        Text("퀴즈 유형을 선택하세요", modifier = Modifier.padding(bottom = 16.dp))

        listOf("word" to "단어 퀴즈", "sentence" to "문장 퀴즈").forEach { (key, label) ->
            Text(
                text = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onTypeSelected(key) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "뒤로가기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onBackClick() }
        )
    }
}

@Preview
@Composable
private fun QuizTypeScreenPreview() {
    MalHaRangTheme {
        QuizTypeScreen(
            padding = PaddingValues(),
            onTypeSelected = {},
            onBackClick = {}
        )
    }
}