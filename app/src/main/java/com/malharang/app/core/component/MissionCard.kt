package com.malharang.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R
import com.malharang.app.ui.theme.MalHaRangTheme

@Composable
fun MissionCard(
    title: String = "Order food",
    description: String = "Learn to order food in Korean",
    onStartClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(5f)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
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
                        tint = Color(0xFF22C55E),
                        modifier = Modifier
                            .size(14.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Button(
                onClick = onStartClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF22C55E),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .defaultMinSize(minHeight = 36.dp)
                    .padding(start = 12.dp)
            ) {
                Text(text = "Start")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMissionCard() {
    MalHaRangTheme {
        MissionCard(
            onClick = {},
            onStartClick = {}
        )
    }
}
