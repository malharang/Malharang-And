package com.malharang.app.presentation.screen.mission.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
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
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun MissionReviews(
    reviewMissions: List<MissionCardModel>,
    title: String = "Completed Missions",
    navigateToChat: (Long?) -> Unit = {}
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
                .noRippleClickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.black
                )
                Text(
                    text = "${reviewMissions.size} missions completed",
                    fontSize = 12.sp,
                    color = colors.grayDark
                )
            }

            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                reviewMissions.forEach { mission ->
                    MissionSummaryCard(
                        mission = mission,
                        navigateToChat = {
                            navigateToChat(mission.conversationId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MissionSummaryCard(
    mission: MissionCardModel,
    navigateToChat: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.greenLight30,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = navigateToChat)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mission.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = colors.black,
            lineHeight = 20.sp,
            modifier = Modifier
                .padding(end = 20.dp)
                .weight(1f)
        )

        Text(
            text = "✓ Done",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = colors.greenDark
        )
    }
}

@Preview
@Composable
private fun MissionReviewsPreview() {
    MalHaRangTheme {
        MissionReviews(
            reviewMissions = listOf(
                MissionCardModel("Asking for Recommendations on Popular Dishes"),
                MissionCardModel("Order food"),
                MissionCardModel("Ask for directions"),
                MissionCardModel("Buy a ticket"),
                MissionCardModel("Introduce yourself")
            )
        )
    }
}
