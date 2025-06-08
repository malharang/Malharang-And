package com.malharang.app.presentation.screen.placetype

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun PlaceTypeRoute(
    onBackClick: () -> Unit,
    padding: PaddingValues,
    onTypeSelected: (List<String>) -> Unit,
    viewModel: PlaceTypeViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val recentTypes by viewModel.recentTypes.collectAsState()
    val selectedTypes by viewModel.selectedTypes.collectAsState()

    BackHandler {
        onTypeSelected(selectedTypes)
        onBackClick()
    }

    PlaceTypeScreen(
        padding = padding,
        query = query,
        onQueryChange = viewModel::updateQuery,
        searchResult = searchResult,
        recentTypes = recentTypes,
        selectedTypes = selectedTypes,
        onTypeSelected = { viewModel.selectPlaceType(it) },
        onBackClick = {
            onTypeSelected(selectedTypes)
            onBackClick()
        }
    )
}

@Composable
fun PlaceTypeScreen(
    padding: PaddingValues,
    query: String,
    onQueryChange: (String) -> Unit,
    searchResult: List<String>,
    recentTypes: List<String>,
    selectedTypes: List<String>,
    onTypeSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(padding)
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
                if (selectedTypes.isNotEmpty()) {
                    Text(
                        text = " (${selectedTypes.size})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colors.greenDark
                        )
                    )
                }
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
                onValueChange = { onQueryChange(it) },
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
                visible = selectedTypes.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text(
                        text = "Select Place Type",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colors.greenDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 18.dp)
                    ) {
                        items(selectedTypes) { type ->
                            PlaceTypePill(
                                type = type,
                                onClick = { onTypeSelected(type) },
                                isSelected = true
                            )
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
                            isSelected = selectedTypes.contains(type)
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
                                isSelected = selectedTypes.contains(type),
                                modifier = Modifier.padding(horizontal = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        if (selectedTypes.isNotEmpty()) {
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
                        .clickable { onBackClick() },
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
                color = if (isSelected) colors.white else colors.greenDark
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlaceTypeScreen() {
    MalHaRangTheme {
        PlaceTypeScreen(
            query = "",
            onQueryChange = {},
            searchResult = listOf("park", "cafe"),
            recentTypes = listOf("museum", "gallery", "restaurant"),
            selectedTypes = listOf("park"),
            onTypeSelected = {},
            onBackClick = {},
            padding = PaddingValues()
        )
    }
}
