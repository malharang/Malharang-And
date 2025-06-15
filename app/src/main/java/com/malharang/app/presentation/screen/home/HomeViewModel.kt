@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceInfoModel
import com.malharang.app.presentation.model.UserStatusModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation: StateFlow<LatLng?> = _currentLocation.asStateFlow()

    private val _placeInfo = MutableStateFlow<PlaceInfoModel?>(null)
    val placeInfo: StateFlow<PlaceInfoModel?> = _placeInfo.asStateFlow()

    private val placesClient: PlacesClient by lazy {
        Places.createClient(context)
    }

    private val excludedTypes = listOf(
        "establishment",
        "point_of_interest"
    )

    val userStatusModel = UserStatusModel(
        profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
        name = "Malssi",
        level = 5,
        exp = 70
    )

    val exampleMissions = listOf(
        MissionCardModel(
            title = "Order at a Cafe",
            description = "Visit a nearby café"
        ),
        MissionCardModel(
            title = "Ask for Directions",
            description = "Visit a nearby café"
        ),
        MissionCardModel(
            title = "Talk at a Convenience Store",
            description = "Visit a nearby café"
        ),
        MissionCardModel(
            title = "Read Traffic Signs",
            description = "Visit a nearby café"
        ),
        MissionCardModel(
            title = "Role-play: Restaurant Order",
            description = "Visit a nearby café"
        ),
        MissionCardModel(
            title = "Introduce Yourself in Korean",
            description = "Visit a nearby café"
        )
    )

    fun fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { loc ->
                loc?.let {
                    _currentLocation.value = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    fun fetchPlaceTypes(placeId: String) {
        val placeFields = listOf(Place.Field.TYPES)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val types = response.place.placeTypes ?: emptyList()

                val filteredTypes = types.filterNot { it in excludedTypes }

                setSelectedPlaceTypes(filteredTypes)
            }
            .addOnFailureListener { exception ->
                setSelectedPlaceTypes(emptyList())
            }
    }

    fun setSelectedPlaceTypes(placeTypes: List<String>) {
        _placeInfo.value = _placeInfo.value?.copy(
            types = placeTypes
        )
    }

    fun setSelectedPlaceInfo(name: String, latLng: LatLng) {
        _placeInfo.value = _placeInfo.value?.copy(
            name = name,
            latLng = latLng
        ) ?: PlaceInfoModel(
            name = name,
            latLng = latLng,
            types = emptyList()
        )
    }

}
