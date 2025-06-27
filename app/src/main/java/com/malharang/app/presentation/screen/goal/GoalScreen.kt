package com.malharang.app.presentation.screen.goal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.malharang.app.R
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun GoalRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onConfirmClick: (String) -> Unit,
    viewModel: GoalViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsState()
    val selectedGoal by viewModel.selectedGoal.collectAsState()

    GoalScreen(
        padding = padding,
        query = query,
        onQueryChange = viewModel::updateQuery,
        onAddGoal = viewModel::addGoalFromQuery,
        selectedGoal = selectedGoal,
        onGoalSelected = viewModel::selectGoal,
        onBackClick = onBackClick,
        onConfirmClick = onConfirmClick
    )
}


@Composable
fun GoalScreen(
    padding: PaddingValues,
    query: String,
    onQueryChange: (String) -> Unit,
    onAddGoal: () -> Unit,
    selectedGoal: String?,
    onGoalSelected: (String) -> Unit,
    onConfirmClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(padding)
    ) {
        // 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 20.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_place_type_arrow_back_24),
                    contentDescription = "뒤로가기",
                    tint = colors.greenDark
                )
            }
            Text(
                text = "Select Goal",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colors.greenDark,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // 입력창
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                placeholder = { Text("e.g. order coffee, ask direction") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colors.white,
                    focusedContainerColor = colors.white,
                    unfocusedIndicatorColor = colors.greenTint,
                    focusedIndicatorColor = colors.greenTint
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onAddGoal()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // 선택된 목표
            AnimatedVisibility(visible = selectedGoal != null, enter = fadeIn(), exit = fadeOut()) {
                Column {
                    Text(
                        text = "Selected Goal",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colors.greenDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    selectedGoal?.let {
                        GoalPill(
                            type = it,
                            onClick = { onGoalSelected(it) },
                            isSelected = true
                        )
                    }
                }
            }
        }

        // Select 버튼
        if (selectedGoal != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.greenDark)
                        .clickable { onConfirmClick(selectedGoal) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Select",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = colors.white,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun GoalPill(
    type: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(
                color = if (isSelected) colors.greenDark else colors.greenTint,
                shape = RoundedCornerShape(22.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 11.dp)
    ) {
        Text(
            text = type,
            style = MaterialTheme.typography.bodyMedium.copy(color = colors.white)
        )
    }
}


@Preview
@Composable
private fun GoalScreenPreview() {
    MalHaRangTheme {
        GoalScreen(
            padding = PaddingValues(0.dp),
            query = "",
            onQueryChange = {},
            onAddGoal = {},
            selectedGoal = null,
            onGoalSelected = {},
            onConfirmClick = {},
            onBackClick = {}
        )
    }
}