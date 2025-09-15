package com.malharang.app.presentation.screen.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import com.malharang.app.presentation.screen.quiz.model.QuizType

@Composable
fun QuizPlayRoute(
    navigateToUp: () -> Unit,
    navigateToQuizResult: () -> Unit,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.quizState?.selectedScenarioId, uiState.quizState?.quizType) {
        val quizState = uiState.quizState
        if (quizState?.selectedScenarioId != null && quizState.quizType != null) {
            val quizTypeString = when (quizState.quizType) {
                QuizType.WORD -> "word"
                QuizType.SENTENCE -> "sentence"
                QuizType.CONVERSATION -> "conversation"
                QuizType.RANDOM -> "random"
            }
            val scenarioId = quizState.selectedScenarioId
        }
    }

    when (uiState.quizLoadingState) {
        QuizContract.QuizLoadingState.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = colors.green
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "퀴즈 생성 중...", style = MalHaRangTheme.typography.bodyMedium.copy(
                            color = colors.grayDark
                        )
                    )
                }
            }
            return
        }

        QuizContract.QuizLoadingState.ERROR -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "퀴즈 로드 실패", style = MalHaRangTheme.typography.titleLarge.copy(
                            color = colors.black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.errorMessage ?: "알 수 없는 오류가 발생했습니다", style = MalHaRangTheme.typography.bodySmall.copy(
                            color = colors.grayDark
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = navigateToUp, colors = ButtonDefaults.buttonColors(
                            containerColor = colors.green
                        )
                    ) {
                        Text(
                            text = "돌아가기", color = colors.white
                        )
                    }
                }
            }
            return
        }

        else -> {
            // 퀴즈 질문이 비어있으면 로딩 상태로 전환
            if (uiState.quizQuestions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        androidx.compose.material3.CircularProgressIndicator(
                            color = colors.green
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "퀴즈 준비 중...", style = MalHaRangTheme.typography.bodyMedium.copy(
                                color = colors.grayDark
                            )
                        )
                    }
                }
                return
            }
        }
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var submitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var correctCount by remember { mutableIntStateOf(0) }

    val current = uiState.quizQuestions[currentIndex]

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
            if (currentIndex == uiState.quizQuestions.lastIndex) {
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
private fun QuizPlayScreen(
    question: String, options: List<String>, isSubmitted: Boolean, isCorrect: Boolean?, selectedAnswer: String?, onAnswerSelected: (String) -> Unit, onNextClick: () -> Unit, onBackClick: () -> Unit, modifier: Modifier = Modifier, currentQuestionIndex: Int = 0, totalQuestions: Int = 1
) {
    val progress = (currentQuestionIndex + 1).toFloat() / totalQuestions

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        colors.greenUltraLight, colors.white, colors.greenLight.copy(alpha = 0.2f)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Modern Header with Progress
            ModernQuizHeader(
                currentQuestion = currentQuestionIndex + 1, totalQuestions = totalQuestions, progress = progress, onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedContent(
                targetState = isSubmitted, transitionSpec = {
                    slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                }) { submitted ->
                if (submitted) {
                    // Result Screen
                    ModernResultDisplay(
                        isCorrect = isCorrect ?: false, correctAnswer = selectedAnswer ?: "", onNextClick = onNextClick
                    )
                } else {
                    // Question Screen  
                    ModernQuestionDisplay(
                        question = question, options = options, selectedAnswer = selectedAnswer, onAnswerSelected = onAnswerSelected
                    )
                }
            }
        }
    }
}


@Composable
private fun ModernQuizHeader(
    currentQuestion: Int, totalQuestions: Int, progress: Float, onBackClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onBackClick() }, shape = RoundedCornerShape(12.dp), color = colors.white, shadowElevation = 4.dp
            ) {
                Icon(
                    Icons.Rounded.ArrowBack, contentDescription = "Back", tint = colors.black, modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Question $currentQuestion of $totalQuestions", style = MalHaRangTheme.typography.bodySmall.copy(
                        color = colors.grayDark
                    )
                )
                Text(
                    text = "Quiz Challenge", style = MalHaRangTheme.typography.bodyMediumBold.copy(
                        color = colors.black
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Bar
        Surface(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), color = colors.greenLight.copy(alpha = 0.3f)
        ) {
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            ) {
                val animatedProgress by animateFloatAsState(
                    targetValue = progress, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
                Surface(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(animatedProgress), color = colors.green, shape = RoundedCornerShape(8.dp)
                ) {}
            }
        }
    }
}

@Composable
private fun ModernQuestionDisplay(
    question: String, options: List<String>, selectedAnswer: String?, onAnswerSelected: (String) -> Unit
) {
    Column {
        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = colors.white), shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = question, style = MalHaRangTheme.typography.bodyMediumBold.copy(
                    color = colors.black, fontSize = 20.sp
                ), modifier = Modifier.padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Answer Options
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            options.forEachIndexed { index, option ->
                AnimatedVisibility(
                    visible = true, enter = fadeIn(
                        animationSpec = tween(300, delayMillis = index * 100)
                    ) + slideInHorizontally(
                        initialOffsetX = { it }, animationSpec = tween(300, delayMillis = index * 100)
                    )
                ) {
                    ModernAnswerOption(
                        text = option, isSelected = selectedAnswer == option, onClick = { onAnswerSelected(option) })
                }
            }
        }
    }
}

@Composable
private fun ModernAnswerOption(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.95f else 1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .scale(scale), elevation = CardDefaults.cardElevation(
        defaultElevation = if (isSelected) 12.dp else 4.dp
    ), colors = CardDefaults.cardColors(
        containerColor = if (isSelected) colors.green else colors.white
    ), shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(24.dp), shape = CircleShape, color = if (isSelected) colors.white else colors.greenLight
            ) {
                if (isSelected) {
                    Icon(
                        Icons.Rounded.CheckCircle, contentDescription = null, tint = colors.green, modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text, style = MalHaRangTheme.typography.bodyMedium.copy(
                    color = if (isSelected) colors.white else colors.black
                ), modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ModernResultDisplay(
    isCorrect: Boolean, correctAnswer: String, onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = true, enter = scaleIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ) + fadeIn()
        ) {
            // Result Icon
            Surface(
                modifier = Modifier.size(120.dp), shape = CircleShape, color = if (isCorrect) colors.green else Color(0xFFFF6B6B), shadowElevation = 16.dp
            ) {
                Icon(
                    if (isCorrect) Icons.Rounded.CheckCircle else Icons.Rounded.Close, contentDescription = null, tint = colors.white, modifier = Modifier.padding(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (isCorrect) "Perfect!" else "Not quite right", style = MalHaRangTheme.typography.titleLarge.copy(
                color = colors.black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isCorrect) {
                "Great job! Keep it up!"
            } else {
                "Don't worry, keep learning!"
            }, style = MalHaRangTheme.typography.bodyMedium.copy(
                color = colors.grayDark
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNextClick, modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colors.green
            )
        ) {
            Text(
                text = "Continue", style = MalHaRangTheme.typography.bodyMediumBold.copy(
                    color = colors.white
                )
            )
        }
    }
}

@Preview
@Composable
private fun QuizPlayScreenPreview() {
    MalHaRangTheme {
        QuizPlayScreen(question = "커피는 영어로?", options = listOf("Water", "Tea", "Coffee"), isSubmitted = false, isCorrect = null, selectedAnswer = null, onAnswerSelected = {}, onNextClick = {}, onBackClick = {})
    }
}