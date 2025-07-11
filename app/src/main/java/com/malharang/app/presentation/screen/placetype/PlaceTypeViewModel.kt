package com.malharang.app.presentation.screen.placetype

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.malharang.app.R
import com.malharang.app.domain.usecase.PlaceTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceTypeViewModel @Inject constructor(
    private val useCase: PlaceTypeUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _selectedType = MutableStateFlow<String?>(null)
    val selectedType: StateFlow<String?> = _selectedType

    private val _recentTypes = MutableStateFlow<List<String>>(emptyList())
    val recentTypes: StateFlow<List<String>> = _recentTypes

    private val _searchResult = MutableStateFlow<List<String>>(emptyList())
    val searchResult: StateFlow<List<String>> = _searchResult

    private var allPlaceTypes: List<String> = emptyList()

    fun initPlaceTypes(context: Context) {
        viewModelScope.launch {
            allPlaceTypes = loadPlaceTypesFromRaw(context)
            _recentTypes.value = useCase.getRecentPlaceTypes()
        }
    }

    private fun loadPlaceTypesFromRaw(context: Context): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.place_types)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<String>>() {}.type)
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery

        if (newQuery.isBlank()) {
            _searchResult.value = emptyList()
            return
        }

        val startsWithResults = allPlaceTypes.filter {
            it.replace("_", " ").startsWith(newQuery.lowercase())
        }
        val containsResults = allPlaceTypes.filter {
            it.replace("_", " ").contains(newQuery.lowercase()) &&
                !it.replace("_", " ").startsWith(newQuery.lowercase())
        }

        _searchResult.value = startsWithResults + containsResults
    }

    fun selectPlaceType(type: String) {
        _selectedType.update { current ->
            if (current == type) null else type
        }

        if (_selectedType.value != null) {
            val updatedRecentTypes = _recentTypes.value
                .toMutableList()
                .apply {
                    remove(type)
                    add(0, type)
                }
                .take(5)

            _recentTypes.value = updatedRecentTypes
        }
    }

    fun getSelectedType(): String? {
        return _selectedType.value
    }
}
