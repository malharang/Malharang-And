@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.malharang.app.R
import com.malharang.app.core.component.UserStatusBar
import com.malharang.app.core.util.toast
import com.malharang.app.presentation.model.MissionCardModel
import com.malharang.app.presentation.model.PlaceInfoModel
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.presentation.screen.home.component.CustomMarker
import com.malharang.app.presentation.screen.home.component.HomeBottomSheet
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    padding: PaddingValues,
    navigateToPlaceType: () -> Unit,
    navigateToGoal: () -> Unit,
    placeTypeArg: String?,
    goalArg: String?,
    viewModel: HomeViewModel = hiltViewModel(),
    zoomLevel: Float = 19f
) {
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )


    val context = LocalContext.current
    val placeInfo by viewModel.placeInfo.collectAsState()
    val missionCardList by viewModel.missionCardList.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val currentLocation by viewModel.currentLocation.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        currentLocation?.let {
            position = CameraPosition.fromLatLngZoom(
                LatLng(it.latitude, it.longitude),
                zoomLevel
            )
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            context.toast(it)
            viewModel.clearToastMessage()
        }
    }

    LaunchedEffect(placeTypeArg) {
        placeTypeArg?.let {
            viewModel.setSelectedPlaceType(it)
        }
    }

    LaunchedEffect(goalArg) {
        if (goalArg != null) {
            viewModel.addGoal(goalArg)
        }
    }


    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.fetchCurrentLocation()
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

    HomeScreen(
        padding = padding,
        context = context,
        userStatusModel = viewModel.userStatusModel,
        onRequestCurrentLocation = {
            viewModel.fetchCurrentLocation()
            moveCameraPosition(currentLocation, cameraPositionState, zoomLevel)
        },
        currentLocation = currentLocation,
        placeInfo = placeInfo,
        onClickPOI = {
            viewModel.clearPlaceInfo()
            viewModel.fetchPlaceType(it.placeId)
            viewModel.setSelectedPlaceInfo(it.name, it.latLng)
        },
        isMissionLoading = isLoading,
        navigateToPlaceType = navigateToPlaceType,
        navigateToGoal = navigateToGoal,
        onGoalRemoveClick = viewModel::removeGoalAt,
        missionCards = missionCardList,
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
    context: Context,
    userStatusModel: UserStatusModel,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng? = null,
    placeInfo: PlaceInfoModel? = null,
    onClickPOI: (PointOfInterest) -> Unit = {},
    missionCards: List<MissionCardModel>,
    isMissionLoading: Boolean = false,
    navigateToPlaceType: () -> Unit = {},
    navigateToGoal: () -> Unit = {},
    onGoalRemoveClick: (Int) -> Unit = {},
    onRequestCurrentLocation: () -> Unit = {}
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val selectedMarkerState = remember(placeInfo?.latLng) {
        placeInfo?.let { MarkerState(it.latLng) }
    }

    val currentMarkerState = remember(currentLocation) {
        currentLocation?.let { MarkerState(currentLocation) }
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
                    myLocationButtonEnabled = true
                ),
                googleMapOptionsFactory = {
                    GoogleMapOptions().mapId("f818388e77495a353ad721f7")
                },
                onPOIClick = { poi ->
                    onClickPOI(poi)
                }
            ) {
                selectedMarkerState?.let {
                    CustomMarker(
                        position = it.position,
                        context = context,
                        resId = R.drawable.img_home_map_malssi_marker
                    )
                }
                currentMarkerState?.let {
                    CustomMarker(
                        position = it.position,
                        context = context,
                        resId = R.drawable.img_home_map_profile_marker
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
                            locationType = placeInfo?.locationType,
                            goalTypes = placeInfo?.goalTypes ?: listOf(),
                            missionCards = missionCards,
                            isMissionLoading = isMissionLoading,
                            onLocationTypeClick = navigateToPlaceType,
                            onGoalClick = navigateToGoal,
                            onGoalRemoveClick = onGoalRemoveClick
                        )
                    }
                ) {
                    placeInfo?.let {
                        LaunchedEffect(it.locationType) {
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
                profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&siAze=64",
                name = "Malssi",
                level = 5,
                exp = 70
            ),
            cameraPositionState = rememberCameraPositionState(),
            missionCards = listOf(),
            navigateToPlaceType = {},
            navigateToGoal = {},
            onGoalRemoveClick = {},
            onRequestCurrentLocation = {},
            context = TODO(),
        )
    }
}
