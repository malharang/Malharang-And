package com.malharang.app.presentation.screen.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _selectedGoal = MutableStateFlow<String?>(null)
    val selectedGoal: StateFlow<String?> = _selectedGoal

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun addGoalFromQuery() {
        val trimmed = _query.value.trim()
        if (trimmed.isNotEmpty()) {
            _selectedGoal.value = trimmed
            _query.value = ""  // 입력창 초기화
        }
    }

    fun selectGoal(goal: String) {
        _selectedGoal.value = goal
    }

    fun getSelectedGoal(): String? = _selectedGoal.value
}
