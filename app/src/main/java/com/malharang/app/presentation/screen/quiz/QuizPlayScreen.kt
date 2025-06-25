package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.presentation.model.QuizQuestionModel
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun QuizPlayRoute(
    padding: PaddingValues,
    scenarioId: Int,
    type: String,
    navigateToQuizResult: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val quizList = remember {
        listOf(
            QuizQuestionModel(
                question = "커피는 영어로?",
                options = listOf("Water", "Tea", "Coffee"),
                answer = "Coffee"
            ),
            QuizQuestionModel(
                question = "화장실은 영어로?",
                options = listOf("Toilet", "Kitchen", "Library"),
                answer = "Toilet"
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var submitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var correctCount by remember { mutableIntStateOf(0) }

    val current = quizList[currentIndex]

    QuizPlayScreen(
        padding = padding,
        question = current.question,
        options = current.options,
        isSubmitted = submitted,
        isCorrect = isCorrect,
        selectedAnswer = selectedAnswer,
        onAnswerSelected = { answer ->
            submitted = true
            selectedAnswer = answer
            isCorrect = answer == current.answer
            if (isCorrect == true) correctCount++
        },
        onNextClick = {
            if (currentIndex == quizList.lastIndex) {
                val result = "$correctCount / ${quizList.size}"
                navigateToQuizResult(result)
            } else {
                currentIndex++
                submitted = false
                selectedAnswer = null
                isCorrect = null
            }
        }
    )
}

@Composable
fun QuizPlayScreen(
    padding: PaddingValues,
    question: String,
    options: List<String>,
    isSubmitted: Boolean,
    isCorrect: Boolean?,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE6F0FA)) // 연한 하늘색
            .padding(padding)
            .padding(horizontal = 24.dp)
    ) {
        if (isSubmitted) {
            // 전체 화면: 정답 피드백만
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isCorrect == true) "✅ 정답입니다!" else "❌ 오답입니다.",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (isCorrect == true) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                )

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onNextClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("다음 문제")
                }
            }
        } else {
            // 문제와 보기 표시
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = question,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(40.dp))

                options.forEach { answer ->
                    Button(
                        onClick = { onAnswerSelected(answer) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A90E2),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = answer)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuizPlayScreenPreview() {
    MalHaRangTheme {
        QuizPlayScreen(
            padding = PaddingValues(),
            question = "커피는 영어로?",
            options = listOf("Water", "Tea", "Coffee"),
            isSubmitted = false,
            isCorrect = null,
            selectedAnswer = null,
            onAnswerSelected = {},
            onNextClick = {},
        )
    }
}