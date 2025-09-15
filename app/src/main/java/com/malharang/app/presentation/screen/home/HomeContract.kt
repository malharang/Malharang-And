package com.malharang.app.presentation.screen.home

import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.LatLng
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceInfoModel
import com.malharang.app.presentation.model.UserStatusModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class HomeContract {
    @Immutable
    data class HomeUiState(
        val currentLocation: LatLng? = null,
        val placeInfo: PlaceInfoModel? = null,
        val missionCardList: ImmutableList<MissionCardModel> = persistentListOf(),
        val errorMessage: String? = null,
        val isLoading: Boolean = false,
        val goalQuery: String = "",
        val userStatusModel: UserStatusModel = UserStatusModel(
            profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
            name = "Malssi",
            level = 5,
            exp = 70
        )
    )

    sealed interface HomeSideEffect {
        data class ShowToast(val message: String) : HomeSideEffect
        data class NavigateToChat(val conversationId: Long) : HomeSideEffect
        data object ClearToastMessage : HomeSideEffect
    }
}