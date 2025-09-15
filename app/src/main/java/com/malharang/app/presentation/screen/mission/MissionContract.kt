package com.malharang.app.presentation.screen.mission

import androidx.compose.runtime.Immutable
import com.malharang.app.domain.model.ExportSentenceData
import com.malharang.app.presentation.model.MissionCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class MissionContract {
    @Immutable
    data class MissionUiState(
        val availableMissions: ImmutableList<MissionCardModel> = persistentListOf(),
        val reviewMissions: ImmutableList<MissionCardModel> = persistentListOf(),
        val exportList: ImmutableList<ExportSentenceData> = persistentListOf(),
        val ttsPlayingId: Long? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface MissionSideEffect {
        data class NavigateToChat(val conversationId: Long) : MissionSideEffect
        data object NavigateToQuizStart : MissionSideEffect
        data class ShowToast(val message: String) : MissionSideEffect
        data class PlayTTS(val id: Long, val text: String) : MissionSideEffect
        data object StopTTS : MissionSideEffect
    }
}