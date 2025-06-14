@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.malharang.app.R
import com.malharang.app.core.component.UserStatusBar
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.presentation.screen.home.component.HomeBottomSheet
import com.malharang.app.presentation.screen.home.component.HomeSearchBar
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeRoute(
    padding: PaddingValues,
    navController: NavController,
    navigateToPlaceType: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // TODO: Dummy Data
    val userStatusModel = UserStatusModel(
        profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
        name = "Malssi",
        level = 5,
        exp = 70
    )

    // Maps
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val location by viewModel.location.collectAsState()
    var cameraOffsetLatLng = LatLng(location.latitude - 0.003, location.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraOffsetLatLng, 16f)
    }

    val selectedPOI by viewModel.selectedPOI.collectAsState()
    val placeTypes by viewModel.placeTypes.collectAsState()

    val navBackStackEntry = navController.currentBackStackEntryAsState().value

    LaunchedEffect(navBackStackEntry) {
        val type = navBackStackEntry?.savedStateHandle?.get<List<String>>("selected_place_types")
        if (type != null) {
            val currentTypes = viewModel.placeTypes.value
            val updatedTypes = (currentTypes + type).distinct()
            viewModel.setSelectedPlaceType(updatedTypes)

            navBackStackEntry.savedStateHandle.remove<List<String>>("selected_place_types")
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag("DEBUG_HOME").d("LaunchedEffect: HomeRoute")
        if (locationPermissions.allPermissionsGranted) {
            viewModel.fetchCurrentLocation()
        } else {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    fun offsetLatLng(location: LatLng): LatLng {
        return LatLng(location.latitude - 0.003, location.longitude)
    }

    LaunchedEffect(location) {
        location.let {
            if (!cameraPositionState.isMoving) {
                cameraPositionState.move(
                    CameraUpdateFactory.newLatLngZoom(offsetLatLng(location), 16f)
                )
            }
        }
    }

    val navigateToPlaceTypeWithData = {
        navController.currentBackStackEntry?.savedStateHandle?.set("existing_types", placeTypes)
        navigateToPlaceType()
    }

    HomeScreen(
        padding = padding,
        userStatusModel = userStatusModel,
        onRequestCurrentLocation = {
            viewModel.fetchCurrentLocation()
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(offsetLatLng(location), 16f))
        },
        navigateToPlaceType = navigateToPlaceTypeWithData,
        navigateToSearch = navigateToSearch,
        selectedPOI = selectedPOI,
        placeTypes = placeTypes,
        missionCards = viewModel.exampleMissions,
        selectPOI = viewModel::selectPOI,
        cameraPositionState = cameraPositionState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    padding: PaddingValues,
    userStatusModel: UserStatusModel,
    cameraPositionState: CameraPositionState,
    selectedPOI: PointOfInterest? = null,
    placeTypes: List<String>,
    missionCards: List<MissionCardModel>,
    navigateToPlaceType: () -> Unit = {},
    navigateToSearch: () -> Unit = {},
    selectPOI: (PointOfInterest) -> Unit = {},
    onRequestCurrentLocation: () -> Unit = {},
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val showSearchBar = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        UserStatusBar(userStatusModel, modifier = Modifier.padding(top = 5.dp, bottom = 3.dp))


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.black),
            contentAlignment = Alignment.BottomCenter
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true,
                ),
                onPOIClick = { poi ->
                    selectPOI(poi)
                },
            ) {
                selectedPOI?.let {
                    Timber.tag("DEBUG_HOME").d("POI Id: %s", it.placeId)

                    Marker(
                        state = MarkerState(position = it.latLng),
                        title = it.name,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                }
            }

            Column {
                AnimatedVisibility(
                    visible = showSearchBar.value,
                    exit = slideOutVertically(
                        targetOffsetY = { -it + 50 },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    HomeSearchBar(
                        onClick = {
                            showSearchBar.value = false
                            scope.launch {
                                delay(300)
                                navigateToSearch()
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(colors.white60)
                        .clickable { onRequestCurrentLocation() }
                        .padding(6.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_home_my_location_24),
                        contentDescription = "내 위치로 이동",
                        colorFilter = ColorFilter.tint(colors.grayDark)
                    )
                }

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 80.dp,
                    sheetContainerColor = colors.white,
                    sheetContent = {
                        HomeBottomSheet(
                            selectedPOIName = selectedPOI?.name,
                            placeTypes = placeTypes,
                            missionCards = missionCards,
                            onAddPlaceTypeClick = navigateToPlaceType,
                        )
                    }
                ) {
                    LaunchedEffect(placeTypes) {
                        if (placeTypes.isNotEmpty()) {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    MalHaRangTheme {
        HomeScreen(
            padding = PaddingValues(),
            userStatusModel = UserStatusModel(
                profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
                name = "Malssi",
                level = 5,
                exp = 70
            ),
            cameraPositionState = rememberCameraPositionState(),
            placeTypes = listOf("park", "restaurant", "restaurant", "restaurant", "restaurant", "restaurant"),
            missionCards = listOf(),
            selectedPOI = null,
            selectPOI = {},
            onRequestCurrentLocation = {},
        )
    }
}
