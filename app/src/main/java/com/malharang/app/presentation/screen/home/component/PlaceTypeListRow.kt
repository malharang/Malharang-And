package com.malharang.app.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun PlaceTypeListRow(
    placeTypes: List<String>,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(placeTypes) { placeType ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(colors.black)
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            ) {
                Text(
                    text = placeType.replace("_", " "),
                    color = colors.white
                )
            }
        }
        item {
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.greenTint)
                    .padding(4.dp)
                    .noRippleClickable { onAddClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_home_add_24),
                    contentDescription = "장소 유형 추가",
                    colorFilter = ColorFilter.tint(colors.white)
                )
            }
        }
    }
}
