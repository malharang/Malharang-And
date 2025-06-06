package com.malharang.app.presentation.screen.placetype

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.domain.usecase.PlaceTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceTypeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: PlaceTypeUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _selectedTypes = MutableStateFlow<List<String>>(emptyList())
    val selectedTypes: StateFlow<List<String>> = _selectedTypes

    private val _recentTypes = MutableStateFlow<List<String>>(emptyList())
    val recentTypes: StateFlow<List<String>> = _recentTypes

    private val allPlaceTypes = listOf(
        "restaurant", "cafe", "bar", "bakery",
        "park", "museum", "art_gallery", "library",
        "shopping_mall", "supermarket", "convenience_store",
        "gym", "hospital", "pharmacy",
        "bank", "atm", "post_office",
        "school", "university",
        "movie_theater", "tourist_attraction",
        "hotel", "parking"
    )

    private val _searchResult = MutableStateFlow<List<String>>(emptyList())
    val searchResult: StateFlow<List<String>> = _searchResult

    init {
        savedStateHandle.get<List<String>>("existing_types")?.let { types ->
            _selectedTypes.value = types.toList()
        }

        viewModelScope.launch {
            _recentTypes.value = useCase.getRecentPlaceTypes()
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isEmpty()) {
            _searchResult.value = emptyList()
        } else {
            val startsWithResults = allPlaceTypes.filter {
                it.replace("_", " ").startsWith(newQuery.lowercase())
            }
            val containsResults = allPlaceTypes.filter {
                it.replace("_", " ").contains(newQuery.lowercase()) && !it.replace("_", " ").startsWith(newQuery.lowercase())
            }
            _searchResult.value = startsWithResults + containsResults
        }
    }

    fun selectPlaceType(type: String) {
        val currentTypes = _selectedTypes.value.toMutableList()
        if (currentTypes.contains(type)) {
            currentTypes.remove(type)
        } else {
            currentTypes.add(type)
        }
        _selectedTypes.value = currentTypes

        val updatedRecentTypes = (
            _recentTypes.value.toMutableList().apply {
                remove(type)
                add(0, type)
            }
            ).take(5)

        _recentTypes.value = updatedRecentTypes
    }

    fun getSelectedTypes(): List<String> {
        return _selectedTypes.value
    }
}
