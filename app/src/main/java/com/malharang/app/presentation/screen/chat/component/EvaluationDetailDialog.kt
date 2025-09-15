package com.malharang.app.presentation.screen.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.malharang.app.R
import com.malharang.app.core.designsystem.theme.MalHaRangTheme
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.colors
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.typography
import com.malharang.app.domain.model.ContextualityData
import com.malharang.app.domain.model.EvaluationResponseData
import com.malharang.app.domain.model.EvaluationResultData
import com.malharang.app.domain.model.GrammarData
import com.malharang.app.domain.model.GrammarErrorData

@Composable
fun EvaluationDetailDialog(
    evaluationData: EvaluationResponseData,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = colors.white),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Evaluation Result",
                        style = typography.bodyMediumBold.copy(fontWeight = FontWeight.Bold),
                        color = colors.black
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (evaluationData.data.contextuality.pass) R.drawable.ic_check_22
                            else R.drawable.ic_caution_14
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Content area: 제한된 maxHeight 안에서 스크롤되도록 LazyColumn 사용
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 520.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Contextuality Section
                    item {
                        EvaluationSection(
                            title = "Contextuality",
                            passed = evaluationData.data.contextuality.pass,
                            comment = evaluationData.data.contextuality.comment
                        )
                    }

                    item {
                        EvaluationSection(
                            title = "Grammar",
                            passed = evaluationData.data.grammar.pass,
                            comment = evaluationData.data.grammar.comment
                        )
                    }

                    // Grammar Errors
                    if (evaluationData.data.grammar.grammar.isNotEmpty()) {
                        item {
                            Text(
                                text = "Grammar Errors",
                                style = typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = colors.black,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        items(evaluationData.data.grammar.grammar) { grammarError ->
                            GrammarErrorCard(grammarError = grammarError)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "Close",
                            style = typography.bodyMedium,
                            color = colors.greenBasic
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EvaluationSection(
    title: String,
    passed: Boolean,
    comment: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (passed) colors.green else colors.secondaryRed,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (passed) R.drawable.ic_check_22 else R.drawable.ic_caution_14
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )

            Text(
                text = title,
                style = typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = colors.black,
                modifier = Modifier.weight(1f)
            )

            // PASS / FAIL badge — minWidth 를 줘서 길이 있어도 안정적 레이아웃
            Text(
                text = if (passed) "PASS" else "FAIL",
                style = typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = if (passed) colors.green else colors.secondaryRed,
                modifier = Modifier
                    .background(
                        color = if (passed) colors.greenLight30 else colors.secondaryRed.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        if (comment.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment,
                style = typography.bodySmall,
                color = colors.black,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun GrammarErrorCard(
    grammarError: GrammarErrorData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = colors.greenLight30.copy(alpha = 0.45f)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Original",
                        style = typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = colors.secondaryRed
                    )
                    Text(
                        text = grammarError.originalSentence,
                        style = typography.bodySmall,
                        color = colors.black,
                        modifier = Modifier
                            .padding(top = 6.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Correction",
                        style = typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = colors.greenBasic
                    )
                    Text(
                        text = grammarError.correctedSentence,
                        style = typography.bodySmall,
                        color = colors.black,
                        modifier = Modifier
                            .padding(top = 6.dp)
                    )
                }
            }

            if (grammarError.simpleExplanation.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colors.grayDark
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = grammarError.simpleExplanation,
                    style = typography.bodySmall,
                    color = colors.grayDark
                )
            }
        }
    }
}


@Preview
@Composable
private fun EvaluationDetailDialogPreview() {
    MalHaRangTheme {
        EvaluationDetailDialog(
            evaluationData = EvaluationResponseData(
                data = EvaluationResultData(
                    contextuality = ContextualityData(
                        comment = "사용자의 응답이 상황에 적절하고 대화 흐름에 맞습니다.",
                        contextuality = emptyList(),
                        pass = true
                    ),
                    grammar = GrammarData(
                        comment = "문법적으로 약간의 오류가 있습니다.",
                        grammar = listOf(
                            GrammarErrorData(
                                originalSentence = "라지로 주세요",
                                correctedSentence = "라지를 주세요",
                                errorExplanation = "조사 '로'가 잘못 사용되었습니다.",
                                simpleExplanation = "음료를 주문할 때는 '를' 조사를 사용해야 합니다."
                            )
                        ),
                        pass = false
                    )
                ),
                message = "평가 완료",
                success = true
            ),
            onDismiss = {}
        )
    }
}