package com.malharang.app.presentation.screen.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R.drawable
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.screen.quiz.model.QuizType

@Composable
fun QuizTypeRoute(
    navigateToUp: () -> Unit,
    navigateToQuizPlay: () -> Unit,
    viewModel: QuizViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is QuizContract.QuizSideEffect.NavigateToNextStep -> {
                    if (sideEffect.step == com.malharang.app.presentation.screen.quiz.model.QuizStep.PLAYING) {
                        navigateToQuizPlay()
                    }
                }

                else -> {}
            }
        }
    }

    QuizTypeScreen(
        onTypeSelected = { type ->
            viewModel.selectQuizType(type)
        },
        onBackClick = navigateToUp
    )
}

@Composable
fun QuizTypeScreen(
    onTypeSelected: (QuizType) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        colors.greenUltraLight,
                        colors.white,
                        colors.greenLight.copy(alpha = 0.2f)
                    )
                )
            )
            .padding(20.dp)
            .systemBarsPadding()
    ) {
        ModernQuizTypeHeader(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(600)) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
            ) {
                ModernQuizTypeCard(
                    title = "Word Quiz",
                    description = "Test your vocabulary knowledge",
                    imageResId = drawable.img_quiz_word,
                    iconBackground = colors.green,
                    onClick = { onTypeSelected(QuizType.WORD) }
                )
            }

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(600, delayMillis = 200)) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                )
            ) {
                ModernQuizTypeCard(
                    title = "Sentence Quiz",
                    description = "Practice sentence construction",
                    imageResId = drawable.img_quiz_sentence,
                    iconBackground = colors.greenDark,
                    onClick = { onTypeSelected(QuizType.SENTENCE) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom hint text
        Text(
            text = "Choose the quiz type that matches your learning goals",
            style = MalHaRangTheme.typography.bodySmall.copy(
                color = colors.grayDark
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ModernQuizTypeHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(44.dp)
                .noRippleClickable(
                    onClick = onBackClick
                ),
            shape = RoundedCornerShape(12.dp),
            color = colors.white,
            shadowElevation = 4.dp
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(drawable.ic_chat_arrow_back_black_24),
                contentDescription = "Back",
                tint = colors.black,
                modifier = Modifier
                    .padding(10.dp)
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "Quiz Type",
                style = MalHaRangTheme.typography.titleLarge.copy(
                    color = colors.black
                )
            )
            Text(
                text = "Choose your challenge",
                style = MalHaRangTheme.typography.bodyMedium.copy(
                    color = colors.grayDark
                )
            )
        }
    }
}

@Composable
fun ModernQuizTypeCard(
    title: String,
    description: String,
    imageResId: Int,
    iconBackground: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .scale(scale)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.white
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = colors.white
                        )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MalHaRangTheme.typography.bodyMediumBold.copy(
                            color = colors.black,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        style = MalHaRangTheme.typography.bodySmall.copy(
                            color = colors.grayDark
                        )
                    )
                }

                // Image with circular background
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = iconBackground,
                    shadowElevation = 8.dp
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = title,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(48.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuizTypeScreenPreview() {
    MalHaRangTheme {
        QuizTypeScreen(
            onTypeSelected = {},
            onBackClick = {}
        )
    }
}
