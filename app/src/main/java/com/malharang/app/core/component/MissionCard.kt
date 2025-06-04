package com.malharang.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@Composable
fun MissionCard(
    data: MissionCardModel =
        MissionCardModel(
            title = "Order food",
            description = "Learn to order food in Korean"
        ),
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true
            )
            .clip(RoundedCornerShape(16.dp))
            .background(colors.white, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_core_missioncard_leaf),
                        contentDescription = null,
                        tint = colors.greenTint,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = data.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = colors.black
                    )
                }

                Text(
                    text = data.description,
                    fontSize = 12.sp,
                    color = colors.grayDark
                )
            }

            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = 24.dp)
                    .padding(start = 12.dp)
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
                title = "Order",
                description = "Learn to order food in Korean"
            )
        )
    }
}
