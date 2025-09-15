package com.malharang.app.presentation.screen.placetype

import androidx.compose.runtime.Immutable
import com.malharang.app.presentation.model.PlaceTypeItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class PlaceTypeContract {
    @Immutable
    data class PlaceTypeUiState(
        val placeTypes: ImmutableList<PlaceTypeItem.Location> = persistentListOf(),
        val selectedPlaceType: PlaceTypeItem.Location? = null,
        val searchQuery: String = "",
        val filteredPlaceTypes: ImmutableList<PlaceTypeItem.Location> = persistentListOf(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface PlaceTypeSideEffect {
        data class NavigateBack(val selectedPlaceType: String) : PlaceTypeSideEffect
        data class ShowToast(val message: String) : PlaceTypeSideEffect
        data object NavigateUp : PlaceTypeSideEffect
    }
}