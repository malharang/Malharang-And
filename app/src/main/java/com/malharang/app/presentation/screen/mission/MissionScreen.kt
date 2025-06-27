package com.malharang.app.presentation.screen.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R
import com.malharang.app.core.component.MissionCard
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.model.ExportSentenceModel
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun MissionRoute(
    padding: PaddingValues
) {
    val missionCardList = listOf(
        MissionCardModel(
            title = "Order food",
        ),
        MissionCardModel(
            title = "Ask for directions",
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
        padding = padding,
        missionCardList = missionCardList,
        reviewMissionList = reviewMissions,
        exportSentences = exportSentences

    )
}

@Composable
private fun MissionScreen(
    padding: PaddingValues,
    missionCardList: List<MissionCardModel>,
    reviewMissionList: List<MissionCardModel>,
    exportSentences: List<ExportSentenceModel>
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

            // 섹션: Available Missions
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Available Missions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                missionCardList.forEach { missionCardData ->
                    MissionCard(data = missionCardData)
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
                DropdownMissions("Review Mission", reviewMissionList)
                SentenceItem("Export Sentences", exportSentences)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
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
                title = "Order food",
            ),
            MissionCardModel(
                title = "Ask for directions",
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
            missionCardList = missionCardList,
            reviewMissionList = reviewMissions,
            exportSentences = exportSentences

        )
    }
}
