package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun QuizStartRoute(
    navigateToUp: () -> Unit,
    navigateToQuizType: () -> Unit,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
) {
    val reviewMissions by viewModel.reviewMissions.collectAsStateWithLifecycle()
    val isInitialized = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (!isInitialized.value) {
            viewModel.getFinishedConversations()
            isInitialized.value = true
        }
    }

    QuizStartScreen(
        scenarioList = reviewMissions,
        onScenarioSelected = { selectedMission ->
            viewModel.selectScenario(selectedMission.conversationId?.toInt() ?: 0)
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.white)
    ) {
        Text(
            text = "Choose a scenario to start the quiz",
            style = MalHaRangTheme.typography.bodyMediumBold,
            modifier = Modifier.padding(20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        scenarioList.forEachIndexed { index, scenario ->
            ScenarioCard(
                scenarioTitle = scenario.title,
                onClick = { onScenarioSelected(scenario) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )
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