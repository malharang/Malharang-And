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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
    navigateToQuizStart: () -> Unit,
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
        missionCardList = missionCardList,
        reviewMissionList = reviewMissions,
        exportSentences = exportSentences,
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
    missionCardList: List<MissionCardModel>,
    reviewMissionList: List<MissionCardModel>,
    exportSentences: List<ExportSentenceModel>,
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

            StartQuizButton(
                onClick =  navigateToQuizStart,
            )
//            Text(
//                text = "go to quiz",
//                modifier = Modifier.clickable {) },
//            )
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
private fun StartQuizButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "scaleAnimation"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale),
        colors = ButtonDefaults.buttonColors(
            containerColor = MalHaRangTheme.colors.greenTint, // 보라 계열 강조 색
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "퀴즈 시작하기",
            style = MalHaRangTheme.typography.titleLarge
        )
    }
}


@Composable
fun DropdownMissions(
    title: String,
    completedMissionList: List<MissionCardModel>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .background(colors.white, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { expanded = !expanded }, // 아이콘 클릭 처리
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.black
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
        if (expanded) {
            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                completedMissionList.forEach { mission ->
                    MissionCard(mission)
                }
            }
        }
    }
}

@Composable
fun SentenceItem(
    title: String,
    exportSentences: List<ExportSentenceModel>,
    onSoundClick: (ExportSentenceModel) -> Unit = {},
    onBookmarkClick: (ExportSentenceModel) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .background(colors.white, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Title Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.black
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                exportSentences.forEach { sentence ->
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = colors.gray.copy(alpha = 0.08f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f)
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_mission_sound_green_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable { onSoundClick(sentence) }

                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(sentence.text, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(sentence.translation, fontSize = 12.sp, color = colors.grayDark)
                                }
                            }

                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_mission_bookmark_filled),
                                contentDescription = "Bookmark",
                                colorFilter = ColorFilter.tint(colors.greenBasic),
                                modifier = Modifier
                                    .noRippleClickable { onBookmarkClick(sentence) } // ✅ 북마크 클릭
                            )
                        }

                        // Divider
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = colors.black.copy(alpha = 0.2f),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
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
            missionCardList = missionCardList,
            reviewMissionList = reviewMissions,
            exportSentences = exportSentences,
            navigateToQuizStart = {}
        )
    }
}
