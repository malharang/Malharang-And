package com.malharang.app.presentation.screen.mission.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malharang.app.R
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.domain.model.ExportSentenceData
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun MissionSentenceItem(
    exportSentences: List<ExportSentenceData>,
    title: String = "Export Sentences",
    onSoundClick: (Long, String) -> Unit = { _, _ -> },
    onBookmarkClick: (Long) -> Unit = {},
    ttsPlayingId: Long? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .background(colors.white, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Title Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.black
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                exportSentences.forEach { sentence ->
                    val isSoundPlaying = sentence.id == ttsPlayingId

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = colors.gray.copy(alpha = 0.08f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f)
                            ) {
                                Image(
                                    imageVector = if (isSoundPlaying) {
                                        ImageVector.vectorResource(id = R.drawable.ic_chat_stop_green_24)
                                    } else {
                                        ImageVector.vectorResource(id = R.drawable.ic_mission_sound_green_24)
                                    },
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            onSoundClick(
                                                sentence.id,
                                                sentence.sentence
                                            )
                                        }

                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(sentence.sentence, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(sentence.translation, fontSize = 12.sp, color = colors.grayDark)
                                }
                            }

                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_mission_bookmark_filled),
                                contentDescription = "Bookmark",
                                colorFilter = ColorFilter.tint(colors.greenBasic),
                                modifier = Modifier
                                    .clickable { onBookmarkClick(sentence.id) }
                            )
                        }

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = colors.black.copy(alpha = 0.2f),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
