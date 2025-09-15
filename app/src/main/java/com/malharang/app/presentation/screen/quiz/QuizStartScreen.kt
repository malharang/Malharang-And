package com.malharang.app.presentation.screen.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.model.MissionCardModel

@Composable
fun QuizStartRoute(
    navigateToQuizType: () -> Unit,
    viewModel: QuizViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.resetQuiz()
        viewModel.loadConversations()
    }

    QuizStartScreen(
        conversations = uiState.conversations.toList(),
        isLoading = uiState.isLoading,
        onConversationSelected = { conversation ->
            viewModel.selectConversation(conversation)
            navigateToQuizType()
        }
    )
}

@Composable
fun QuizStartScreen(
    conversations: List<MissionCardModel>,
    isLoading: Boolean,
    onConversationSelected: (MissionCardModel) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.CircularProgressIndicator(
                    color = colors.green
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading conversations...",
                    style = MalHaRangTheme.typography.bodyMedium.copy(
                        color = colors.grayDark
                    )
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                QuizStartHeader()
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(conversations) { index, conversation ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 300, delayMillis = index * 100)
                    ) + slideInVertically(
                        initialOffsetY = { it / 3 },
                        animationSpec = tween(durationMillis = 300, delayMillis = index * 100)
                    )
                ) {
                    ConversationCard(
                        conversation = conversation,
                        onClick = { onConversationSelected(conversation) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun QuizStartHeader() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Search,
                contentDescription = null,
                tint = colors.green,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Quiz Challenge",
                style = MalHaRangTheme.typography.titleLarge.copy(
                    color = colors.black
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Choose a scenario and test your language skills!",
            style = MalHaRangTheme.typography.bodyMedium.copy(
                color = colors.grayDark
            )
        )
    }
}

@Composable
fun ConversationCard(
    conversation: MissionCardModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.white
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colors.green.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp)
            ) {}

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = conversation.title,
                        style = MalHaRangTheme.typography.bodyMediumBold.copy(
                            color = colors.black
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Test your skills with this conversation",
                        style = MalHaRangTheme.typography.bodySmall.copy(
                            color = colors.grayDark
                        )
                    )
                }

                Icon(
                    Icons.Rounded.Search,
                    contentDescription = null,
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuizStartScreenPreview() {
    MalHaRangTheme {
        QuizStartScreen(
            conversations = listOf(),
            isLoading = false,
            onConversationSelected = {}
        )
    }
}
