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
import androidx.compose.runtime.collectAsState
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
import com.malharang.app.domain.model.ExportSentenceData
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
    val exportList by viewModel.exportList.collectAsStateWithLifecycle()

    val ttsPlayingId by viewModel.ttsPlayingId.collectAsState()

    MissionScreen(
        padding = padding,
        availableMissions = availableMissions,
        reviewMissions = reviewMissions,
        exportSentences = exportList,
        navigateToChat = { id ->
            if (id != null) {
                viewModel.saveRecentConversationId(id)
                navigateToChat()
            }
        },
        onExportBookmarkClick = { },
        onExportSoundClick = { id, text ->
            viewModel.playOrStopTTS(id = id, text = text)
        },
        ttsPlayingId = ttsPlayingId
    )
}

@Composable
private fun MissionScreen(
    padding: PaddingValues,
    availableMissions: List<MissionCardModel>,
    reviewMissions: List<MissionCardModel>,
    exportSentences: List<ExportSentenceData>,
    navigateToChat: (Long?) -> Unit,
    onExportBookmarkClick: (Long) -> Unit = {},
    onExportSoundClick: (Long, String) -> Unit = { _, _ -> },
    ttsPlayingId: Long? = null
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
                        onClick = { navigateToChat(missionCardData.conversationId) }
                    )
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
                    navigateToChat = navigateToChat
                )
                MissionSentenceItem(
                    exportSentences = exportSentences,
                    onBookmarkClick = onExportBookmarkClick,
                    onSoundClick = onExportSoundClick,
                    ttsPlayingId = ttsPlayingId
                )
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

        MissionScreen(
            padding = PaddingValues(),
            exportSentences = emptyList(),
            availableMissions = missionCardList,
            reviewMissions = reviewMissions,
            navigateToChat = {}
        )
    }
}
