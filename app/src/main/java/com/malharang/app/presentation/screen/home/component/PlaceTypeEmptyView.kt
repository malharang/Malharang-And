package com.malharang.app.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun PlaceTypeEmptyView(
    onAddPlaceTypeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "이 장소에 대한 유형 정보가 없어요.\n직접 추가해 볼까요?",
            modifier = Modifier.padding(bottom = 12.dp),
            textAlign = TextAlign.Center,
            color = colors.grayDark
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(colors.greenTint)
                .clickable { onAddPlaceTypeClick() }
                .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_home_add_24),
                    contentDescription = "장소 유형 추가",
                    colorFilter = ColorFilter.tint(colors.white)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "장소 유형 직접 추가",
                    color = colors.white
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlaceTypeEmptyViewPreview() {
    MalHaRangTheme {
        PlaceTypeEmptyView {
        }
    }
}
