package com.malharang.app.presentation.screen.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.model.ExportSentenceData
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.domain.usecase.GetExportSentencesUseCase
import com.malharang.app.domain.usecase.TTSUseCase
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.screen.chat.component.SpeechRecorderManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _availableMissions = MutableStateFlow<List<MissionCardModel>>(emptyList())
    val availableMissions: StateFlow<List<MissionCardModel>> = _availableMissions

    private val _reviewMissions = MutableStateFlow<List<MissionCardModel>>(emptyList())
    val reviewMissions: StateFlow<List<MissionCardModel>> = _reviewMissions

    private val _exportList = MutableStateFlow<List<ExportSentenceData>>(emptyList())
    val exportList: StateFlow<List<ExportSentenceData>> = _exportList

    private val _ttsPlayingId = MutableStateFlow<Long?>(null)
    val ttsPlayingId: StateFlow<Long?> = _ttsPlayingId

    init {
        viewModelScope.launch {
            val allConversations = getAllConversationsUseCase()

            val available = allConversations
                .filter { it.mode == "role_play" }
                .map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        conversationId = it.id
                    )
                }

            val review = allConversations
                .filter { it.mode == "finished" }
                .map {
                    MissionCardModel(
                        title = it.selectedScenario,
                        conversationId = it.id
                    )
                }

            _availableMissions.value = available
            _reviewMissions.value = review
        }

        loadExportedSentences()
    }

    fun saveRecentConversationId(id: Long) {
        viewModelScope.launch {
            conversationIdDataStore.saveConversationId(id)
        }
    }

    private fun loadExportedSentences() {
        viewModelScope.launch {
            _exportList.value = getExportSentencesUseCase()
        }
    }

    fun playOrStopTTS(id: Long, text: String) {
        viewModelScope.launch {
            val isPlaying = _ttsPlayingId.value == id

            if (isPlaying) {
                recorder.stopTTS {
                    _ttsPlayingId.value = null
                }
                return@launch
            }

            _ttsPlayingId.value = id

            ttsUseCase(text)
                .onSuccess { ttsData ->
                    recorder.playTTSStream(
                        responseBody = ttsData.audioStream,
                        onComplete = {
                            _ttsPlayingId.value = null
                        }
                    )
                }
                .onFailure {
                    _ttsPlayingId.value = null
                }
        }
    }
}
