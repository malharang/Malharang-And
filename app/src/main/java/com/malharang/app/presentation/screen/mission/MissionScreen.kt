package com.malharang.app.presentation.screen.mission

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.core.component.MissionCard
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme

@Composable
fun MissionRoute(
    padding: PaddingValues
) {
    MissionScreen(padding = padding)
}

@Composable
private fun MissionScreen(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 상단: 타이틀과 필터 아이콘
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
            MissionCard(
                title = "Order food",
                description = "Learn to order food in Korean",
                onClick = {}
            )
            MissionCard(
                title = "Ask for directions",
                description = "Practice asking for directions",
                onClick = {}
            )
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
            DropdownMissions("Review Mission")
            SentenceItem("Export Sentences")
        }
    }
}
data class MissionData(val title: String, val description: String)

@Composable
fun DropdownMissions(title: String) {
    var expanded by remember { mutableStateOf(false) }

    val completedMissions = listOf(
        MissionData("Order food", "Learn to order food in Korean"),
        MissionData("Ask for directions", "Practice asking for directions"),
        MissionData("Buy a ticket", "Handle ticket buying situation"),
        MissionData("Introduce yourself", "Practice self introduction"),
        MissionData("Make a reservation", "Phone call reservation practice")
    )

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
                items(completedMissions) { mission ->
                    MissionCard(
                        title = mission.title,
                        description = mission.description,
                        onClick = {},
                        onStartClick = {}
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun SentenceItem(title: String) {
    var expanded by remember { mutableStateOf(false) }

    val completedMissions = listOf(
        "Order food - 2025.05.01",
        "Ask for directions - 2025.05.03",
        "Introduce yourself - 2025.05.04",
        "Buy a ticket - 2025.05.05",
        "Make a reservation - 2025.05.06"
    )

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
                items(completedMissions) { mission ->
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
        MissionScreen(padding = PaddingValues())
    }
}
