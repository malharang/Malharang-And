package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.malharang.app.R
import com.malharang.app.presentation.screen.quiz.model.QuizType
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun QuizPlayRoute(
    navigateToUp: () -> Unit,
    navigateToQuizResult: () -> Unit,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val quizList by viewModel.quizList.collectAsState()

    // 최초 한 번 메시지 + 퀴즈 로딩
    LaunchedEffect(state.selectedScenarioId, state.quizType) {
        if (state.selectedScenarioId != null && state.quizType != null) {
            val quizTypeString = when(state.quizType) {
                QuizType.WORD -> "word"
                QuizType.SENTENCE -> "sentence"
                null -> return@LaunchedEffect
            }
            val scenarioId = state.selectedScenarioId ?: return@LaunchedEffect
            viewModel.loadMessages(scenarioId.toLong(), quizTypeString)
        }
    }

    if (quizList.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Quiz loading...")
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var submitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var correctCount by remember { mutableIntStateOf(0) }

    val current = quizList[currentIndex]

    QuizPlayScreen(
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
                navigateToQuizResult()
            } else {
                currentIndex++
                submitted = false
                selectedAnswer = null
                isCorrect = null
            }
        },
        onBackClick = navigateToUp,
        modifier = modifier,
    )
}

@Composable
fun QuizPlayScreen(
    question: String,
    options: List<String>,
    isSubmitted: Boolean,
    isCorrect: Boolean?,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE6F0FA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 🔙 Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_place_type_arrow_back_24),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .padding(end = 12.dp)
                        .size(24.dp)
                )
                Text(
                    text = "Quiz",
                    style = MalHaRangTheme.typography.bodyMediumBold,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            if (isSubmitted) {
                // ✅ Submitted: Show result
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isCorrect == true) "✅ Correct!" else "❌ Wrong!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (isCorrect == true) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onNextClick,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next")
                    }
                }
            } else {
                // ❓ Quiz mode
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
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
            question = "커피는 영어로?",
            options = listOf("Water", "Tea", "Coffee"),
            isSubmitted = false,
            isCorrect = null,
            selectedAnswer = null,
            onAnswerSelected = {},
            onNextClick = {},
            onBackClick = {}
        )
    }
}