package com.malharang.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@Composable
fun UserStatusBar(
    userStatusModel: UserStatusModel,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = userStatusModel.profileUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = colors.gray,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = userStatusModel.name,
                style = typography.bodyMediumBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 100.dp)
            ) {
                Text(
                    text = "Lv ${userStatusModel.level}",
                    modifier = Modifier.padding(end = 5.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(colors.gray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(userStatusModel.exp * 0.01f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp))
                            .background(colors.green)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserStatusBarPreview() {
    MalHaRangTheme {
        UserStatusBar(
            userStatusModel = UserStatusModel(
                profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
                name = "Malssi",
                level = 5,
                exp = 70,
            ),
            modifier = Modifier.background(colors.white)
        )
    }
}
