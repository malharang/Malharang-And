package com.malharang.app.presentation.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.malharang.app.core.component.MissionCard
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceTypeItem
import com.malharang.app.ui.theme.MalHaRangTheme.colors

@Composable
fun HomeBottomSheet(
    selectedPOIName: String?,
    goalTypes: List<PlaceTypeItem.Goal>,
    missionCards: List<MissionCardModel>,
    onLocationTypeClick: () -> Unit,
    onMissionCardClick: (String?) -> Unit,
    onGoalClick: () -> Unit,
    onGoalRemoveClick: (Int) -> Unit,
    locationType: PlaceTypeItem.Location? = null,
    isMissionLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = selectedPOIName?.let { "\uD83D\uDCCD Location: $it" }
                ?: "\uD83D\uDCCD Pick a spot to talk!",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (locationType != null) {
            PlaceTypeListRow(
                goalTypes = goalTypes,
                onLocationTypeClick = onLocationTypeClick,
                onGoalClick = onGoalClick,
                onPillClick = onGoalRemoveClick,
                locationType = locationType
            )
            Spacer(modifier = Modifier.padding(5.dp))

            if (isMissionLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(vertical = 50.dp),
                    color = colors.greenTint
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                ) {
                    items(missionCards) { card ->
                        MissionCard(
                            data = card,
                            onClick = {
                                onMissionCardClick(card.title)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }
        } else {
            PlaceTypeEmptyView(
                onAddPlaceTypeClick = onLocationTypeClick
            )
        }
    }
}
