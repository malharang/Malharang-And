@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.malharang.app.data.local.datastore.ConversationDataStore
import com.malharang.app.domain.model.ConversationData
import com.malharang.app.domain.usecase.GetAllConversationsUseCase
import com.malharang.app.domain.usecase.InsertConversationUseCase
import com.malharang.app.domain.usecase.ScenarioUseCase
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceInfoModel
import com.malharang.app.presentation.model.PlaceTypeItem
import com.malharang.app.presentation.screen.home.HomeContract.HomeSideEffect
import com.malharang.app.presentation.screen.home.HomeContract.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
class HomeViewModel @Inject constructor(
    private val conversationIdDataStore: ConversationDataStore,
    private val locationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context,
    private val scenarioUseCase: ScenarioUseCase,
    private val insertConversationUseCase: InsertConversationUseCase,
    private val getAllConversationsUseCase: GetAllConversationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val placesClient: PlacesClient by lazy {
        Places.createClient(context)
    }

    fun saveRecentConversationId(id: Long) {
        viewModelScope.launch {
            conversationIdDataStore.saveConversationId(id)
        }
    }

    fun fetchScenario(placeType: String, goal: String?) {
        viewModelScope.launch {
            if (goal == null) {
                updateIsLoading(true)
            }
            scenarioUseCase(location = placeType, goal = goal)
                .onSuccess { scenarioList ->
                    val itemType = if (goal != null) PlaceTypeItem.Goal(goal) else PlaceTypeItem.Location(placeType)
                    val newCards = scenarioList.map {
                        MissionCardModel(
                            title = it.title,
                            type = itemType
                        )
                    }

                    if (goal != null) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                missionCardList = (currentState.missionCardList + newCards).toImmutableList()
                            )
                        }
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(missionCardList = newCards.toImmutableList())
                        }
                    }
                }
                .onFailure { error ->
                    updateErrorMessage(error.message)
                }
            updateIsLoading(false)
        }
    }

    fun fetchPlaceType(placeId: String) {
        val placeFields = listOf(Place.Field.PRIMARY_TYPE)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val primaryType = response.place.primaryType
                setSelectedPlaceType(primaryType)
            }
            .addOnFailureListener {
                setSelectedPlaceType(null)
            }
    }

    fun setSelectedPlaceType(placeType: String?) {
        if (placeType != null) {
            val newPlaceInfo = _uiState.value.placeInfo?.copy(
                locationType = PlaceTypeItem.Location(name = placeType),
                goalTypes = emptyList()
            ) ?: PlaceInfoModel(
                name = placeType.replace("_", " "),
                latLng = LatLng(0.0, 0.0),
                locationType = PlaceTypeItem.Location(name = placeType),
                goalTypes = emptyList()
            )

            _uiState.update { currentState ->
                currentState.copy(placeInfo = newPlaceInfo)
            }
            fetchScenario(placeType, null)
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    placeInfo = currentState.placeInfo?.copy(locationType = null)
                )
            }
        }
    }

    fun addGoal(goal: String) {
        val newGoal = PlaceTypeItem.Goal(goal)
        val currentInfo = _uiState.value.placeInfo ?: return
        val updatedGoals = currentInfo.goalTypes.filterNot { it.name == goal } + newGoal

        _uiState.update { currentState ->
            currentState.copy(
                placeInfo = currentInfo.copy(goalTypes = updatedGoals)
            )
        }

        currentInfo.locationType?.name?.let { placeType ->
            fetchScenario(placeType, goal)
        }
    }

    fun removeGoalAt(index: Int) {
        val currentInfo = _uiState.value.placeInfo ?: return

        if (index < 0 || index >= currentInfo.goalTypes.size) return

        val updatedGoals = currentInfo.goalTypes.toMutableList().also {
            it.removeAt(index)
        }

        _uiState.update { currentState ->
            currentState.copy(
                placeInfo = currentInfo.copy(goalTypes = updatedGoals)
            )
        }
    }

    fun fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { loc ->
                loc?.let {
                    _uiState.update { currentState ->
                        currentState.copy(currentLocation = LatLng(it.latitude, it.longitude))
                    }
                }
            }
        }
    }

    fun setSelectedPlaceInfo(name: String, latLng: LatLng) {
        val currentPlaceInfo = _uiState.value.placeInfo
        val newPlaceInfo = currentPlaceInfo?.copy(
            name = name,
            latLng = latLng
        ) ?: PlaceInfoModel(
            name = name,
            latLng = latLng,
            locationType = currentPlaceInfo?.locationType,
            goalTypes = currentPlaceInfo?.goalTypes ?: emptyList()
        )

        _uiState.update { currentState ->
            currentState.copy(placeInfo = newPlaceInfo)
        }
    }

    fun saveScenario(
        scenarioTitle: String?,
        onComplete: (Long) -> Unit
    ) {
        val location = _uiState.value.placeInfo?.locationType?.name
        if (location.isNullOrEmpty()) {
            updateErrorMessage("장소 정보가 없습니다.")
            return
        }

        if (scenarioTitle.isNullOrEmpty()) {
            updateErrorMessage("시나리오 제목이 비어 있습니다.")
            return
        }

        viewModelScope.launch {
            try {
                val existingConversations = getAllConversationsUseCase()
                val existingConversation = existingConversations.find { conversation ->
                    conversation.selectedScenario == scenarioTitle &&
                            conversation.mode != "finished"
                }

                if (existingConversation != null) {
                    onComplete(existingConversation.id)
                } else {
                    val newConversation = ConversationData(
                        id = 0L,
                        mode = "scenario_selection",
                        selectedLocation = location,
                        selectedScenario = scenarioTitle
                    )
                    val conversationId = insertConversationUseCase(newConversation)
                    onComplete(conversationId)
                }
            } catch (e: Exception) {
                updateErrorMessage("시나리오 처리 실패: ${e.localizedMessage}")
            }
        }
    }

    fun clearPlaceInfo() {
        _uiState.update { currentState ->
            currentState.copy(
                placeInfo = null,
                missionCardList = emptyList<MissionCardModel>().toImmutableList()
            )
        }
    }

    fun clearToastMessage() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }

    fun updateGoalQuery(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(goalQuery = newQuery)
        }
    }

    fun clearGoalQuery() {
        _uiState.update { currentState ->
            currentState.copy(goalQuery = "")
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
                _sideEffect.emit(HomeSideEffect.ShowToast(it))
            }
        }
    }
}