package com.malharang.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceTypeItem
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@Composable
fun MissionCard(
    data: MissionCardModel,
    onClick: () -> Unit = {}
) {
    val borderColor = when (data.type) {
        is PlaceTypeItem.Goal -> colors.greenTint
        is PlaceTypeItem.Location -> colors.transparent
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp), clip = true)
            .clip(RoundedCornerShape(16.dp))
            .background(colors.white)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp), // 수평 패딩만
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = data.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colors.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = 24.dp)
                    .clip(RoundedCornerShape(30))
                    .background(colors.greenTint)
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start",
                    style = typography.bodySmall,
                    color = colors.white
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMissionCard() {
    MalHaRangTheme {
        MissionCard(
            MissionCardModel(
                title = "ordering food",
                type = PlaceTypeItem.Location(name = "장소")
            )
        )
    }
}
