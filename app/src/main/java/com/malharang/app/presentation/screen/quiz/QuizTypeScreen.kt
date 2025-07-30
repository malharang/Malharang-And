package com.malharang.app.presentation.screen.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R
import com.malharang.app.R.drawable
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun QuizTypeRoute(
    padding: PaddingValues,
    navigateToQuizPlay: (String) -> Unit, // "word" 또는 "sentence"
    onBackClick: () -> Unit
) {
    QuizTypeScreen(
        padding = padding,
        onTypeSelected = { type ->
            navigateToQuizPlay(type)
        },
        onBackClick = onBackClick
    )
}

@Composable
fun QuizTypeScreen(
    padding: PaddingValues,
    onTypeSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(colors.white)
            .padding(20.dp)
    ) {
        // 상단 뒤로가기 버튼 + 제목
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.ic_place_type_arrow_back_24), // ← 여기에 원하는 뒤로가기 아이콘 리소),
                contentDescription = "Back",
                tint = colors.black,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(end = 12.dp) // 👉 end padding 적용
                    .size(24.dp)
            )

            Text(
                text = "Choose a quiz type",
                style = MalHaRangTheme.typography.bodyMediumBold,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(250.dp))

        // 가운데 정렬된 가로 이미지 버튼 배치
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuizTypeButton(
                label = "Word Quiz",
                imageResId = drawable.img_quiz_word, // word 관련 아이콘 리소스
                onClick = { onTypeSelected("word") }
            )
            QuizTypeButton(
                label = "Sentence Quiz",
                imageResId = drawable.img_quiz_sentence, // sentence 관련 아이콘 리소스
                onClick = { onTypeSelected("sentence") }
            )
        }
    }
}

@Composable
fun QuizTypeButton(
    label: String,
    imageResId: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = colors.green, // 말하랑의 테마 색상
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = Modifier
                .size(96.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = label,
            style = MalHaRangTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun QuizTypeScreenPreview() {
    MalHaRangTheme {
        QuizTypeScreen(
            padding = PaddingValues(),
            onTypeSelected = {},
            onBackClick = {}
        )
    }
}