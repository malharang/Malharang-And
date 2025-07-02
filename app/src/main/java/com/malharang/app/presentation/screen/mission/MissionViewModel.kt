package com.malharang.app.presentation.screen.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.presentation.model.MissionCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val getAllConversationsUseCase: GetAllConversationsUseCase,
    private val conversationIdDataStore: ConversationDataStore,
) : ViewModel() {

    private val _availableMissions = MutableStateFlow<List<MissionCardModel>>(emptyList())
    val availableMissions: StateFlow<List<MissionCardModel>> = _availableMissions

    private val _reviewMissions = MutableStateFlow<List<MissionCardModel>>(emptyList())
    val reviewMissions: StateFlow<List<MissionCardModel>> = _reviewMissions

    init {
        viewModelScope.launch {
            val allConversations = getAllConversationsUseCase()

            val available = allConversations
                .filter { it.mode == "role_play" }
                .map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        conversationId = it.id,
                    )
                }

            val review = allConversations
                .filter { it.mode == "finished" }
                .map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        conversationId = it.id,
                    )
                }

            _availableMissions.value = available
            _reviewMissions.value = review
        }
    }

    fun saveRecentConversationId(id: Long) {
        viewModelScope.launch {
            conversationIdDataStore.saveConversationId(id)
        }
    }
}
