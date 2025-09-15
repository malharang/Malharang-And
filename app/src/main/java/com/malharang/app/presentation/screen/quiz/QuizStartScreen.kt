package com.malharang.app.presentation.screen.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors

@Composable
fun QuizStartRoute(
    navigateToUp: () -> Unit,
    navigateToQuizType: () -> Unit,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isInitialized = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (!isInitialized.value) {
            viewModel.loadConversations()
            isInitialized.value = true
        }
    }

    QuizStartScreen(
        scenarioList = uiState.conversations.toList(),
        onScenarioSelected = { selectedMission ->
            viewModel.selectConversation(selectedMission)
            navigateToQuizType()
        },
        onBackClick = navigateToUp,
        modifier = modifier,
    )
}

@Composable
fun QuizStartScreen(
    scenarioList: List<MissionCardModel>,
    onScenarioSelected: (MissionCardModel) -> Unit,
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
                        colors.white
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                QuizStartHeader()
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(scenarioList) { index, scenario ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 300, delayMillis = index * 100)
                    ) + slideInVertically(
                        initialOffsetY = { it / 3 },
                        animationSpec = tween(durationMillis = 300, delayMillis = index * 100)
                    )
                ) {
                    ModernScenarioCard(
                        scenario = scenario,
                        onClick = { onScenarioSelected(scenario) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun QuizStartHeader() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Search,
                contentDescription = null,
                tint = colors.green,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Quiz Challenge",
                style = MalHaRangTheme.typography.titleLarge.copy(
                    color = colors.black
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Choose a scenario and test your language skills!",
            style = MalHaRangTheme.typography.bodyMedium.copy(
                color = colors.grayDark
            )
        )
    }
}

@Composable
fun ModernScenarioCard(
    scenario: MissionCardModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.white
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            // Glassmorphism 효과를 위한 배경
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colors.green.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp)
            ) {}

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 아이콘 배경
                Surface(
                    modifier = Modifier.size(48.dp),
                    color = colors.green,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 4.dp
                ) {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = null,
                        tint = colors.white,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = scenario.title,
                        style = MalHaRangTheme.typography.bodyMediumBold.copy(
                            color = colors.black
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ready to challenge yourself?",
                        style = MalHaRangTheme.typography.bodySmall.copy(
                            color = colors.grayDark
                        )
                    )
                }

                // 화살표 표시
                Icon(
                    Icons.Rounded.Star, // 임시로 Star 아이콘 사용, 실제로는 화살표 아이콘
                    contentDescription = null,
                    tint = colors.green,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ScenarioCard(
    scenarioTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = colors.green, // 말하랑의 테마 색상
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = scenarioTitle,
            style = MalHaRangTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = colors.black
            )
        )
    }
}


@Preview
@Composable
private fun QuizStartScreenPreview() {
    MalHaRangTheme {
        QuizStartScreen(
            scenarioList = listOf(),
            onScenarioSelected = {},
            onBackClick = {},
        )
    }
}