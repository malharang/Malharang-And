package com.malharang.app.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malharang.app.R
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors

@Composable
fun GoalRoute(
    modifier: Modifier = Modifier,
    navigateToUp: () -> Unit,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query = uiState.goalQuery

    GoalScreen(
        modifier = modifier,
        query = query,
        onQueryChange = viewModel::updateGoalQuery,
        onBackClick = navigateToUp,
        onConfirmClick = {
            viewModel.addGoal(it)
            viewModel.clearGoalQuery()
            navigateToUp()
        }
    )
}

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onConfirmClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(colors.white)
            .imePadding()
    ) {
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
                        if (query.trim().isNotEmpty()) {
                            onConfirmClick(query.trim())
                        }
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            if (query.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.greenDark)
                        .clickable { onConfirmClick(query.trim()) },
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

@Preview
@Composable
private fun GoalScreenPreview() {
    MalHaRangTheme {
        GoalScreen(
            query = "",
            onQueryChange = {},
            onConfirmClick = {},
            onBackClick = {}
        )
    }
}
