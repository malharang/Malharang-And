package com.malharang.app.presentation.screen.mission

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malharang.app.R
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
    navigateToQuizStart: () -> Unit,
    navigateToChat: () -> Unit,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val availableMissions by viewModel.availableMissions.collectAsStateWithLifecycle()
    val reviewMissions by viewModel.reviewMissions.collectAsStateWithLifecycle()
    val exportList by viewModel.exportList.collectAsStateWithLifecycle()

    val ttsPlayingId by viewModel.ttsPlayingId.collectAsState()

    MissionScreen(
        padding = padding,
        navigateToQuizStart = navigateToQuizStart,
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
        ttsPlayingId = ttsPlayingId,
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
    ttsPlayingId: Long? = null,
    navigateToQuizStart: () -> Unit
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

            QuizEntryCard(onClick = navigateToQuizStart)
            Spacer(modifier = Modifier.height(20.dp))
            // 섹션: Available Missions
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

@Composable
fun QuizEntryCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        label = "quiz_card_scale"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp))
            .background(
                color = colors.white,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.img_quiz_icon1),
                contentDescription = "Quiz Icon",
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = "Start Quiz",
                    style = MalHaRangTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Check your Korean skills with fun questions!",
                    style = MalHaRangTheme.typography.bodySmall,
                )
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
            navigateToChat = {},
            navigateToQuizStart = {},
        )
    }
}
