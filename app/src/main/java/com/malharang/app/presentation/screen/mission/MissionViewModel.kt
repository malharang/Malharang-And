package com.malharang.app.presentation.screen.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.domain.usecase.GetExportSentencesUseCase
import com.malharang.app.domain.usecase.TTSUseCase
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceTypeItem
import com.malharang.app.presentation.screen.chat.component.SpeechRecorderManager
import com.malharang.app.presentation.screen.mission.MissionContract.MissionSideEffect
import com.malharang.app.presentation.screen.mission.MissionContract.MissionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val getAllConversationsUseCase: GetAllConversationsUseCase,
    private val conversationIdDataStore: ConversationDataStore,
    private val getExportSentencesUseCase: GetExportSentencesUseCase,
    private val ttsUseCase: TTSUseCase,
    private val recorder: SpeechRecorderManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MissionUiState())
    val uiState: StateFlow<MissionUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MissionSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        loadMissions()
        loadExportSentences()
    }

    private fun loadMissions() {
        viewModelScope.launch {
            updateIsLoading(true)
            try {
                val conversations = getAllConversationsUseCase()

                val available = conversations.filter { it.mode != "finished" }.map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        type = PlaceTypeItem.Location(it.selectedLocation),
                        conversationId = it.id
                    )
                }

                val completed = conversations.filter { it.mode == "finished" }.map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        type = PlaceTypeItem.Location(it.selectedLocation),
                        conversationId = it.id
                    )
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        availableMissions = available.toImmutableList(),
                        reviewMissions = completed.toImmutableList()
                    )
                }
            } catch (e: Exception) {
                updateErrorMessage("Failed to load missions: ${e.localizedMessage}")
            }
            updateIsLoading(false)
        }
    }

    private fun loadExportSentences() {
        viewModelScope.launch {
            try {
                val exportSentences = getExportSentencesUseCase()
                _uiState.update { currentState ->
                    currentState.copy(exportList = exportSentences.toImmutableList())
                }
            } catch (e: Exception) {
                updateErrorMessage("Failed to load export sentences: ${e.localizedMessage}")
            }
        }
    }

    fun saveRecentConversationId(id: Long) {
        viewModelScope.launch {
            conversationIdDataStore.saveConversationId(id)
        }
    }

    fun playOrStopTTS(id: Long, text: String) {
        viewModelScope.launch {
            val currentPlayingId = _uiState.value.ttsPlayingId

            if (currentPlayingId == id) {
                // Stop current TTS
                recorder.stopTTS {
                    _uiState.update { currentState ->
                        currentState.copy(ttsPlayingId = null)
                    }
                }
                _sideEffect.emit(MissionSideEffect.StopTTS)
            } else {
                // Stop any current TTS and start new one
                recorder.stopTTS { }

                _uiState.update { currentState ->
                    currentState.copy(ttsPlayingId = id)
                }

                ttsUseCase(text)
                    .onSuccess { ttsData ->
                        recorder.playTTSStream(
                            responseBody = ttsData.audioStream,
                            onComplete = {
                                _uiState.update { currentState ->
                                    currentState.copy(ttsPlayingId = null)
                                }
                            }
                        )
                    }
                    .onFailure { throwable ->
                        _uiState.update { currentState ->
                            currentState.copy(ttsPlayingId = null)
                        }
                        updateErrorMessage("TTS Error: ${throwable.message}")
                    }

                _sideEffect.emit(MissionSideEffect.PlayTTS(id, text))
            }
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
        message?.let {
            viewModelScope.launch {
                _sideEffect.emit(MissionSideEffect.ShowToast(it))
            }
        }
    }
}