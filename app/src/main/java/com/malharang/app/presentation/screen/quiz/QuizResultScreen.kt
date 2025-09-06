package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@Composable
fun QuizResultRoute(
    navigateToUp: () -> Unit,
    navigateToQuizStart: () -> Unit,
    navigateToMission: () -> Unit,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    QuizResultScreen(
        countResult = "Quiz completed!",
        onRetryClick = navigateToQuizStart,
        onCompleteClick = navigateToMission,
        onBackClick = navigateToUp,
        modifier = modifier
    )
}

@Composable
fun QuizResultScreen(
    countResult: String,
    onRetryClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "퀴즈 결과",
            style = typography.bodyMediumBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$countResult 정답",
            style = typography.titleLarge,
            color = Color(0xFF4A90E2)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onRetryClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("다시 풀기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCompleteClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("퀘스트로 이동")
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "뒤로가기",
            modifier = Modifier
                .clickable { onBackClick() }
                .padding(top = 16.dp)
        )
    }
}

@Preview
@Composable
private fun QuizResultScreenPreview() {
    MalHaRangTheme {
        QuizResultScreen(
            countResult = "10 / 10",
            onRetryClick = {},
            onCompleteClick = {},
            onBackClick = {},
            )
    }
}