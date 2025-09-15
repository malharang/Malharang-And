package com.malharang.app.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun PlaceTypeRoute(
    modifier: Modifier = Modifier,
    navigateToUp: () -> Unit,
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query = uiState.placeTypeQuery
    val searchResult = uiState.placeTypeSearchResult
    val recentTypes = uiState.recentPlaceTypes
    val selectedType = uiState.selectedPlaceType

    LaunchedEffect(Unit) {
        viewModel.initPlaceTypes()
    }

    PlaceTypeScreen(
        modifier = modifier,
        query = query,
        onQueryChange = viewModel::updatePlaceTypeQuery,
        searchResult = searchResult,
        recentTypes = recentTypes,
        selectedType = selectedType,
        onTypeSelected = viewModel::selectPlaceType,
        onBackClick = navigateToUp,
        onConfirmClick = {
            viewModel.clearPlaceTypeQuery()
            navigateToUp()
        }
    )
}

@Composable
fun PlaceTypeScreen(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    searchResult: List<String>,
    recentTypes: List<String>,
    selectedType: String?,
    onTypeSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(colors.white)
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
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Place Type",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colors.greenDark,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
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
                placeholder = { Text("e.g. cafe, museum, park") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colors.white,
                    focusedContainerColor = colors.white,
                    unfocusedIndicatorColor = colors.greenTint,
                    focusedIndicatorColor = colors.greenTint
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colors.greenDark
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_place_type_cancel_24),
                                contentDescription = "지우기",
                                tint = colors.greenDark
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            AnimatedVisibility(
                visible = selectedType != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text(
                        text = "Selected Place Type",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colors.greenDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    selectedType?.let {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 18.dp)
                        ) {
                            item {
                                PlaceTypePill(
                                    type = it,
                                    onClick = { onTypeSelected(it) },
                                    isSelected = true
                                )
                            }
                        }
                    }
                }
            }

            if (query.isEmpty() && recentTypes.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_place_type_history_24),
                        contentDescription = null,
                        tint = colors.greenDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colors.greenDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 18.dp)
                ) {
                    items(recentTypes) { type ->
                        PlaceTypePill(
                            type = type,
                            onClick = { onTypeSelected(type) },
                            isSelected = selectedType == type
                        )
                    }
                }
            }

            if (query.isNotEmpty()) {
                Text(
                    text = "Search Results",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colors.greenDark,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (searchResult.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\uD83D\uDD0D No matching types found!",
                            style = MaterialTheme.typography.bodyLarge.copy(color = colors.grayDark)
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 16.dp)
                    ) {
                        items(searchResult) { type ->
                            PlaceTypePill(
                                type = type,
                                onClick = { onTypeSelected(type) },
                                isSelected = selectedType == type,
                                modifier = Modifier.padding(horizontal = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        if (selectedType != null) {
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
                        .clickable { onConfirmClick() },
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
fun PlaceTypePill(
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
            text = type.replace("_", " "),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colors.white
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlaceTypeScreen() {
    MalHaRangTheme {
        PlaceTypeScreen(
            query = "cafe",
            onQueryChange = {},
            searchResult = listOf("cafe", "restaurant", "bar"),
            recentTypes = listOf("museum", "park"),
            selectedType = "cafe",
            onTypeSelected = {},
            onBackClick = {},
            onConfirmClick = {}
        )
    }
}
