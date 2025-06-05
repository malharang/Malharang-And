@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng>(LatLng(37.5665, 126.9780))
    val location: StateFlow<LatLng> = _location.asStateFlow()

    private val _selectedPOI = MutableStateFlow<PointOfInterest?>(null)
    val selectedPOI: StateFlow<PointOfInterest?> = _selectedPOI.asStateFlow()

    fun fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
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

