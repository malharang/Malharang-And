package com.malharang.app.presentation.screen.mission

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.core.component.MissionCard
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
            description = "Learn to order food in Korean"
        ),
        MissionCardModel(
            title = "Ask for directions",
            description = "Practice asking for directions"
        )
    )

    val reviewMissions = listOf(
        MissionCardModel("Order food", "Learn to order food in Korean"),
        MissionCardModel("Ask for directions", "Practice asking for directions"),
        MissionCardModel("Buy a ticket", "Handle ticket buying situation"),
        MissionCardModel("Introduce yourself", "Practice self introduction"),
        MissionCardModel("Make a reservation", "Phone call reservation practice")
    )

    val exportSentences = listOf(
        "Order food - 2025.05.01",
        "Ask for directions - 2025.05.03",
        "Introduce yourself - 2025.05.04",
        "Buy a ticket - 2025.05.05",
        "Make a reservation - 2025.05.06"
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
    exportSentences: List<String>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }, // 아이콘 클릭 처리
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 280.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                items(completedMissionList) { mission ->
                    MissionCard(mission)
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun SentenceItem(
    title: String,
    exportSentences: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .background(colors.white, RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp), // 스크롤 높이 제한
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(exportSentences) { mission ->
                    Text(
                        text = mission,
                        fontSize = 12.sp,
                        color = colors.black,
                        modifier = Modifier.padding(start = 8.dp)
                    )
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
                description = "Learn to order food in Korean"
            ),
            MissionCardModel(
                title = "Ask for directions",
                description = "Practice asking for directions"
            )
        )

        val reviewMissions = listOf(
            MissionCardModel("Order food", "Learn to order food in Korean"),
            MissionCardModel("Ask for directions", "Practice asking for directions"),
            MissionCardModel("Buy a ticket", "Handle ticket buying situation"),
            MissionCardModel("Introduce yourself", "Practice self introduction"),
            MissionCardModel("Make a reservation", "Phone call reservation practice")
        )

        val exportSentences = listOf(
            "Order food - 2025.05.01",
            "Ask for directions - 2025.05.03",
            "Introduce yourself - 2025.05.04",
            "Buy a ticket - 2025.05.05",
            "Make a reservation - 2025.05.06"
        )

        MissionScreen(
            padding = PaddingValues(),
            missionCardList = missionCardList,
            reviewMissionList = reviewMissions,
            exportSentences = exportSentences

        )
    }
}
