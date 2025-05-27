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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.core.component.MissionCard
import com.malharang.app.ui.theme.MalHaRangTheme

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
                tint = Color.Black
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
                onClick = {},
                onStartClick = {}
            )
            MissionCard(
                title = "Ask for directions",
                description = "Practice asking for directions",
                onClick = {},
                onStartClick = {}
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
            DropdownItem("Review Mission")
            DropdownItem("Export Sentences")
        }
    }
}

@Composable
fun DropdownItem(title: String) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
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
                color = Color.Black
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (expanded) {
            Text(
                text = "미션 결과나 저장된 문장이 여기에 표시됩니다.",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
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
