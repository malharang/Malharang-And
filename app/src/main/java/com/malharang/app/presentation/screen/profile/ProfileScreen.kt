package com.malharang.app.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R
import com.malharang.app.core.component.UserStatusBar
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun ProfileRoute(
    padding: PaddingValues
) {
    ProfileScreen(padding = padding)
}

@Composable
fun ProfileScreen(padding: PaddingValues) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 📦 메인 콘텐츠 덮기
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserStatusBar(
                userStatusModel = UserStatusModel(
                    profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
                    name = "Malssi",
                    level = 5,
                    exp = 70
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileCharacterImage()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEFF9E9))
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                ProfileStatsCard(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                BadgeCard(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileCharacterImage() {
    Image(
        painter = painterResource(id = R.drawable.img_profile_massi), // <- .png 리소스로 교체
        contentDescription = "Seed Character",
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun ProfileStatsCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Available Statis", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Total days studied", fontSize = 12.sp, color = Color.Gray)
                    Text("32", fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Consecutive learning", fontSize = 12.sp, color = Color.Gray)
                    Text("14", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Completed missions", fontSize = 12.sp, color = Color.Gray)
                    Text("12", fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Total XP earnd", fontSize = 12.sp, color = Color.Gray)
                    Text("3,450", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BadgeCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start // ← 왼쪽 정렬
        ) {
            Text("Badges", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.img_profile_badge),
                contentDescription = "Badges",
                modifier = Modifier
                    .height(56.dp)
                    .wrapContentWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileScreen() {
    MalHaRangTheme {
        ProfileScreen(padding = PaddingValues())
    }
}
