package com.malharang.app.presentation.screen.goal

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }
}
