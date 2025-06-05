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
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng>(LatLng(37.5665, 126.9780))
    val location: StateFlow<LatLng> = _location.asStateFlow()

    private val _selectedPOI = MutableStateFlow<PointOfInterest?>(null)
    val selectedPOI: StateFlow<PointOfInterest?> = _selectedPOI.asStateFlow()

    private val placesClient: PlacesClient by lazy {
        Places.createClient(context)
    }

    private val _placeTypes = MutableStateFlow<List<String>>(emptyList())
    val placeTypes: StateFlow<List<String>> = _placeTypes.asStateFlow()

    private val excludedTypes = listOf(
        "establishment",
        "point_of_interest"
    )

    fun fetchPlaceTypes(placeId: String) {
        val placeFields = listOf(Place.Field.TYPES)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val types = response.place.placeTypes ?: emptyList()

                val filteredTypes = types.filterNot { it in excludedTypes }

                _placeTypes.value = filteredTypes
                Timber.tag("DEBUG_HOME").d("Place Types: %s", _placeTypes.value)
            }
            .addOnFailureListener { exception ->
                _placeTypes.value = emptyList()
                Timber.tag("DEBUG_HOME").e(exception, "Place Types: 장소 유형을 가져오는데 실패했습니다.")
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
                    _location.value = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    fun selectPOI(poi: PointOfInterest) {
        _selectedPOI.value = poi
    }
}
