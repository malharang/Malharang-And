package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun QuizStartRoute(
    padding: PaddingValues,
    navigateToQuizType: (Int) -> Unit,
    onBackClick: () -> Unit,
) {

    val scenarioList = listOf(
        "Scenario 1",
        "Scenario 2",
        "Scenario 3",
    )

    QuizStartScreen(
        padding = padding,
        scenarioList = scenarioList,
        onScenarioSelected = { scenarioId -> navigateToQuizType(scenarioId) },
        onBackClick = onBackClick
    )
}

@Composable
fun QuizStartScreen(
    padding: PaddingValues,
    scenarioList: List<String>, // TODO: 전체 종료된 시나리오 바탕으로 선택되 시나리오 변경 해야할듯?
    onScenarioSelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(padding)
            .fillMaxSize()
            .background(colors.white)
            .padding(20.dp)
    ) {
        Text(
            "시나리오를 선택하는 화면" +
                    "" +
                    "여기서 시나리오를 선택해서 " +
                    "onScenarioSelected 콜백을 호출해" +
                    "선택된 시나리오 아이디를 넘겨줘야함"
        )

        Button(
            onClick = { onScenarioSelected(2) }
        ) { Text("타입 선택하기") }
    }
}

@Preview
@Composable
private fun QuizStartScreenPreview() {
    MalHaRangTheme {
        QuizStartScreen(
            padding = PaddingValues(),
            scenarioList = listOf("Scenario 1", "Scenario 2", "Scenario 3"),
            onScenarioSelected = {},
            onBackClick = {},
        )
    }
}