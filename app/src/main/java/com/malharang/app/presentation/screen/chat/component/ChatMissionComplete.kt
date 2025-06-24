package com.malharang.app.presentation.screen.chat.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography
import androidx.compose.foundation.layout.Spacer as Spacer1
import androidx.compose.material3.Icon as Icon1
import androidx.navigation.NavController

@Composable
fun MissionComplete(
    xpGained: Int = 30,
    navController: NavController? = null,
    onCompleteClick: (() -> Unit)? = null,
    onFindAnotherClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = colors.white)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎉 Mission Complete!",
                style = typography.bodySmall,
                color = colors.black,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "+$xpGained XP gained! Your seed character has grown.",
                style = typography.bodySmall,
                color = colors.grayDark
            )

            Button(
                onClick = {
                    onCompleteClick?.invoke() ?: navController?.navigate("mission")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C471))
            ) {
                Icon1(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Complete",
                    tint = colors.white
                )
                Spacer1(modifier = Modifier.width(8.dp))
                Text("Go to Quiz Tab", color = colors.white)
            }

            OutlinedButton(
                onClick = {
                    onFindAnotherClick?.invoke() ?: navController?.navigate("home") //
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon1(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                Spacer1(modifier = Modifier.width(8.dp))
                Text("Find Another Mission")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMissionComplete() {
    MaterialTheme {
        MissionComplete()
    }
}
