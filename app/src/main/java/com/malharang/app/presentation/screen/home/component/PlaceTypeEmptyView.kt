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
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors

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
            text = "No place type available.\nWould you like to add one?",
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
                    contentDescription = "Add place type",
                    colorFilter = ColorFilter.tint(colors.white)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Add place type",
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
