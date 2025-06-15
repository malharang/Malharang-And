@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.google.android.gms.maps.GoogleMapOptions
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
import com.malharang.app.presentation.model.PlaceInfoModel
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.presentation.screen.home.component.HomeBottomSheet
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    padding: PaddingValues,
    navController: NavController,
    navigateToPlaceType: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    zoomLevel: Float = 19f
) {
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val currentLocation by viewModel.currentLocation.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        currentLocation?.let {
            position = CameraPosition.fromLatLngZoom(
                LatLng(it.latitude, it.longitude), zoomLevel
            )
        }
    }

    val placeInfo by viewModel.placeInfo.collectAsState()

    val navBackStackEntry = navController.currentBackStackEntryAsState().value

    LaunchedEffect(navBackStackEntry) {
        val type = navBackStackEntry?.savedStateHandle?.get<List<String>>("selected_place_types")
        if (type != null) {
            val currentTypes = placeInfo?.types ?: emptyList()
            val updatedTypes = (currentTypes + type).distinct()
            viewModel.setSelectedPlaceTypes(updatedTypes)

            navBackStackEntry.savedStateHandle.remove<List<String>>("selected_place_types")
        }
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.fetchCurrentLocation() // 권한 허용 시 내 위치 다시 요청
        }
    }

    LaunchedEffect(Unit) {
        if (!locationPermissions.allPermissionsGranted) {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            moveCameraPosition(it, cameraPositionState, zoomLevel)
        }
    }

    val navigateToPlaceTypeWithData = {
        navController.currentBackStackEntry?.savedStateHandle?.set("existing_types", placeInfo?.types ?: emptyList())
        navigateToPlaceType()
    }

    HomeScreen(
        padding = padding,
        userStatusModel = viewModel.userStatusModel,
        onRequestCurrentLocation = {
            viewModel.fetchCurrentLocation()
            moveCameraPosition(currentLocation, cameraPositionState, zoomLevel)
        },
        placeInfo = placeInfo,
        onClickPOI = {
            viewModel.fetchPlaceTypes(it.placeId)
            viewModel.setSelectedPlaceInfo(it.name, it.latLng)
        },
        navigateToPlaceType = navigateToPlaceTypeWithData,
        missionCards = viewModel.exampleMissions,
        cameraPositionState = cameraPositionState
    )
}

fun moveCameraPosition(currentLocation: LatLng?, cameraPositionState: CameraPositionState, zoomLevel: Float) {
    currentLocation?.let {
        if (!cameraPositionState.isMoving) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(it, zoomLevel)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    padding: PaddingValues,
    userStatusModel: UserStatusModel,
    cameraPositionState: CameraPositionState,
    placeInfo: PlaceInfoModel? = null,
    onClickPOI: (PointOfInterest) -> Unit = {},
    missionCards: List<MissionCardModel>,
    navigateToPlaceType: () -> Unit = {},
    onRequestCurrentLocation: () -> Unit = {},
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val markerState = remember(placeInfo?.latLng) {
        placeInfo?.let { MarkerState(it.latLng) }
    }

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
                googleMapOptionsFactory = {
                    GoogleMapOptions().mapId("f818388e77495a353ad721f7")
                },
                onPOIClick = { poi ->
                    onClickPOI(poi)
                },
            ) {

                markerState?.let {
                    Marker(
                        state = it,
                        title = placeInfo?.name,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                }
            }

            Column {
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
                            selectedPOIName = placeInfo?.name,
                            placeTypes = placeInfo?.types ?: emptyList(),
                            missionCards = missionCards,
                            onAddPlaceTypeClick = navigateToPlaceType,
                        )
                    },
                ) {
                    placeInfo?.let {
                        LaunchedEffect(it.types) {
                            if (it.types.isNotEmpty()) {
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    MalHaRangTheme {
        HomeScreen(
            padding = PaddingValues(),
            userStatusModel = UserStatusModel(
                profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&siAze=64",
                name = "Malssi",
                level = 5,
                exp = 70
            ),
            cameraPositionState = rememberCameraPositionState(),
            missionCards = listOf(),
        )
    }
}
