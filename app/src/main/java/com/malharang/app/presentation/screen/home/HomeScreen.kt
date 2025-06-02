package com.malharang.app.presentation.screen.home

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.VectorPath
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.core.component.MissionCard
import com.malharang.app.core.component.UserStatusBar
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun HomeRoute(
    padding: PaddingValues
) {

    val userStatusModel = UserStatusModel(
        profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
        name = "Malssi",
        level = 5,
        exp = 70,
    )

    HomeScreen(
        padding = padding,
        userStatusModel = userStatusModel,
        currentLocation = "한신대학교 경삼관",
    )
}

@Composable
private fun HomeScreen(
    padding: PaddingValues,
    userStatusModel: UserStatusModel,
    currentLocation: String,
    onLoadMoreContents: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        UserStatusBar(userStatusModel)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.black)
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.white)
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HorizontalDivider(
                    thickness = 4.dp,
                    modifier = Modifier
                        .width(36.dp)
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Text(
                    text = "\uD83D\uDCCD 현재 위치: $currentLocation",
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                )
                MissionCard() // TODO: MissionCardData 로 전달

                Spacer(modifier = Modifier.height(10.dp))

                MissionCard()

                Spacer(modifier = Modifier.height(10.dp))

                MissionCard()

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_home_chevron_down_24),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable { onLoadMoreContents() }
                        .padding(bottom = 15.dp)
                )

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    MalHaRangTheme {
        HomeScreen(
            padding = PaddingValues(),
            userStatusModel = UserStatusModel(
                profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
                name = "Malssi",
                level = 5,
                exp = 70,
            ),
            currentLocation = "한신대학교 경삼관"
        )
    }
}
