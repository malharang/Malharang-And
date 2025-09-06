package com.malharang.app.presentation.screen.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    // 실제 결과 데이터를 받아올 수 있도록 확장 가능
    QuizResultScreen(
        score = 85, // 예시 점수
        totalQuestions = 10,
        correctAnswers = 8,
        onRetryClick = navigateToQuizStart,
        onCompleteClick = navigateToMission,
        onBackClick = navigateToUp,
        modifier = modifier
    )
}

@Composable
fun QuizResultScreen(
    score: Int,
    totalQuestions: Int,
    correctAnswers: Int,
    onRetryClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        colors.greenUltraLight,
                        colors.white,
                        colors.greenLight.copy(alpha = 0.3f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = true,
                enter = scaleIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    )
                ) + fadeIn(animationSpec = tween(800))
            ) {
                ModernResultHeader(
                    score = score,
                    totalQuestions = totalQuestions,
                    correctAnswers = correctAnswers
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 400))
            ) {
                ModernResultStats(
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions,
                    score = score
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 600))
            ) {
                ModernResultActions(
                    onRetryClick = onRetryClick,
                    onCompleteClick = onCompleteClick
                )
            }
        }
    }
}

@Composable
fun ModernResultHeader(
    score: Int,
    totalQuestions: Int,
    correctAnswers: Int
) {
    val isExcellent = score >= 80
    val isGood = score >= 60
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Trophy Icon
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = when {
                isExcellent -> colors.green
                isGood -> colors.greenTint
                else -> colors.grayDark
            },
            shadowElevation = 16.dp
        ) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = null,
                tint = colors.white,
                modifier = Modifier.padding(32.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = when {
                isExcellent -> "Outstanding!"
                isGood -> "Good Job!"
                else -> "Keep Trying!"
            },
            style = typography.titleLarge.copy(
                fontSize = 28.sp,
                color = colors.black
            )
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = when {
                isExcellent -> "You're a language master!"
                isGood -> "You're making great progress!"
                else -> "Practice makes perfect!"
            },
            style = typography.bodyMedium.copy(
                color = colors.grayDark
            )
        )
    }
}

@Composable
fun ModernResultStats(
    correctAnswers: Int,
    totalQuestions: Int,
    score: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = colors.white),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = "Score",
                    value = "${score}%",
                    color = colors.green
                )
                
                StatItem(
                    label = "Correct",
                    value = "$correctAnswers/$totalQuestions",
                    color = colors.greenTint
                )
                
                StatItem(
                    label = "Accuracy",
                    value = "${(correctAnswers.toFloat() / totalQuestions * 100).toInt()}%",
                    color = colors.greenDark
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = colors.greenLight.copy(alpha = 0.3f)
            ) {
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth(correctAnswers.toFloat() / totalQuestions),
                        color = colors.green,
                        shape = RoundedCornerShape(8.dp)
                    ) {}
                }
            }
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = typography.titleLarge.copy(
                color = color,
                fontSize = 24.sp
            )
        )
        Text(
            text = label,
            style = typography.bodySmall.copy(
                color = colors.grayDark
            )
        )
    }
}

@Composable
fun ModernResultActions(
    onRetryClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.green
            )
        ) {
            Icon(
                Icons.Rounded.Refresh,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Try Again",
                style = typography.bodyMediumBold.copy(
                    color = colors.white
                )
            )
        }
        
        OutlinedButton(
            onClick = onCompleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.green
            )
        ) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Back to Missions",
                style = typography.bodyMediumBold
            )
        }
    }
}

@Preview
@Composable
private fun QuizResultScreenPreview() {
    MalHaRangTheme {
        QuizResultScreen(
            score = 85,
            totalQuestions = 10,
            correctAnswers = 8,
            onRetryClick = {},
            onCompleteClick = {},
            onBackClick = {}
        )
    }
}