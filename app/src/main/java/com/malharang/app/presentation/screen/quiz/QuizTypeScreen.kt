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
import com.malharang.app.R.drawable
import com.malharang.app.presentation.screen.quiz.model.QuizType
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun QuizTypeRoute(
    navigateToUp: () -> Unit,
    navigateToQuizPlay: () -> Unit,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
) {
    QuizTypeScreen(
        onTypeSelected = { type ->
            when(type) {
                "word" -> viewModel.selectType(QuizType.WORD)
                "sentence" -> viewModel.selectType(QuizType.SENTENCE)
            }
            navigateToQuizPlay()
        },
        onBackClick = navigateToUp,
        modifier = modifier
    )
}

@Composable
fun QuizTypeScreen(
    onTypeSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.ic_place_type_arrow_back_24),
                contentDescription = "Back",
                tint = colors.black,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(end = 12.dp)
                    .size(24.dp)
            )

            Text(
                text = "Choose a quiz type",
                style = MalHaRangTheme.typography.bodyMediumBold,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(250.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuizTypeButton(
                label = "Word Quiz",
                imageResId = drawable.img_quiz_word,
                onClick = { onTypeSelected("word") }
            )
            QuizTypeButton(
                label = "Sentence Quiz",
                imageResId = drawable.img_quiz_sentence,
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
                color = colors.green,
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
            onTypeSelected = {},
            onBackClick = {}
        )
    }
}