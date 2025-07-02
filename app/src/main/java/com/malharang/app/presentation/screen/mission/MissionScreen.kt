package com.malharang.app.presentation.screen.mission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malharang.app.core.component.MissionCard
import com.malharang.app.presentation.model.ExportSentenceModel
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.screen.mission.component.MissionReviews
import com.malharang.app.presentation.screen.mission.component.MissionSentenceItem
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun MissionRoute(
    padding: PaddingValues,
    navigateToChat: () -> Unit,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val availableMissions by viewModel.availableMissions.collectAsStateWithLifecycle()
    val reviewMissions by viewModel.reviewMissions.collectAsStateWithLifecycle()

    val exportSentences = listOf(
        ExportSentenceModel("Can I have some tissues?", "티슈 좀 얻을 수 있을까?"),
        ExportSentenceModel("How can I go to the Byeongjeom station?", "병점역에 어떻게 가야해?"),
        ExportSentenceModel("My name is Massi!", "내 이름은 말씨야!"),
        ExportSentenceModel("Where can I buy this ticket?", "이 티켓은 어디서 사니?"),
        ExportSentenceModel("I would like to reserve a suite for 4 people", "4인용 스위트룸을 예약하고 싶어."),
        ExportSentenceModel("Can I have some tissues?", "티슈 좀 얻을 수 있을까?"),
        ExportSentenceModel("How can I go to the Byeongjeom station?", "병점역에 어떻게 가야해?"),
        ExportSentenceModel("My name is Massi!", "내 이름은 말씨야!"),
        ExportSentenceModel("Where can I buy this ticket?", "이 티켓은 어디서 사니?"),
        ExportSentenceModel("I would like to reserve a suite for 4 people", "4인용 스위트룸을 예약하고 싶어.")
    )

    MissionScreen(
        padding = padding,
        availableMissions = availableMissions,
        reviewMissions = reviewMissions,
        exportSentences = exportSentences,
        navigateToChat = { id ->
            if (id != null) {
                viewModel.saveRecentConversationId(id)
                navigateToChat()
            }
        }
    )
}

@Composable
private fun MissionScreen(
    padding: PaddingValues,
    availableMissions: List<MissionCardModel>,
    reviewMissions: List<MissionCardModel>,
    exportSentences: List<ExportSentenceModel>,
    navigateToChat: (Long?) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Missions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = colors.black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Available Missions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                availableMissions.forEach { missionCardData ->
                    MissionCard(
                        data = missionCardData,
                        onClick = {navigateToChat(missionCardData.conversationId)})
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Completed Missions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                MissionReviews(
                    reviewMissions = reviewMissions,
                    navigateToChat = navigateToChat,
                )
                MissionSentenceItem(exportSentences)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMissionScreen() {
    MalHaRangTheme {
        val missionCardList = listOf(
            MissionCardModel(
                title = "Order food"
            ),
            MissionCardModel(
                title = "Ask for directions"
            )
        )

        val reviewMissions = listOf(
            MissionCardModel("Order food"),
            MissionCardModel("Ask for directions"),
            MissionCardModel("Buy a ticket"),
            MissionCardModel("Introduce yourself"),
            MissionCardModel("Make a reservation")
        )

        val exportSentences = listOf(
            ExportSentenceModel("Can I have some tissues?", "티슈 좀 얻을 수 있을까?"),
            ExportSentenceModel("How can I go to the Byeongjeom station?", "병점역에 어떻게 가야해?"),
            ExportSentenceModel("My name is Massi!", "내 이름은 말씨야!"),
            ExportSentenceModel("Where can I buy this ticket?", "이 티켓은 어디서 사니?"),
            ExportSentenceModel("I would like to reserve a suite for 4 people", "4인용 스위트룸을 예약하고 싶어."),
            ExportSentenceModel("Can I have some tissues?", "티슈 좀 얻을 수 있을까?"),
            ExportSentenceModel("How can I go to the Byeongjeom station?", "병점역에 어떻게 가야해?"),
            ExportSentenceModel("My name is Massi!", "내 이름은 말씨야!"),
            ExportSentenceModel("Where can I buy this ticket?", "이 티켓은 어디서 사니?"),
            ExportSentenceModel("I would like to reserve a suite for 4 people", "4인용 스위트룸을 예약하고 싶어.")

        )

        MissionScreen(
            padding = PaddingValues(),
            exportSentences = exportSentences,
            availableMissions = missionCardList,
            reviewMissions = reviewMissions,
            navigateToChat = {},
        )
    }
}
